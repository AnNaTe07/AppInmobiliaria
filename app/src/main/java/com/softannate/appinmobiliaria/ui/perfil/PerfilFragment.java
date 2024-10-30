package com.softannate.appinmobiliaria.ui.perfil;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentPerfilBinding;
import com.softannate.appinmobiliaria.modelos.Propietario;



import java.io.InputStream;

public class PerfilFragment extends Fragment {

    private EditText etDni, etNombre, etApellido, etTelefono;
    private ImageView fotoPerfil;

    private PerfilViewModel vmPerfil;
    private FragmentPerfilBinding bindingPerfil;
    private static int REQUEST_IMAGE_CAPTURE=1;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindingPerfil = FragmentPerfilBinding.inflate(getLayoutInflater());
        vmPerfil= new ViewModelProvider(this).get(PerfilViewModel.class);


      //inicializo campos
      etApellido=bindingPerfil.etApellido;
      etDni= bindingPerfil.etDniP;
      etNombre=bindingPerfil.etNombreP;
      etTelefono=bindingPerfil.etTel;
      fotoPerfil=bindingPerfil.imagenPerfil;

        vmPerfil.leerPropietario();

        //Observador p/ propietario
        vmPerfil.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                    etDni.setText(String.valueOf(propietario.getDni()));
                    etApellido.setText(String.valueOf(propietario.getApellido()));
                    etNombre.setText(String.valueOf(propietario.getNombre()));
                    etTelefono.setText(String.valueOf(propietario.getTelefono()));
                    Glide.with(getContext())
                            .load(propietario.getAvatar())
                            .placeholder(R.drawable.cargando) // Imagen temporal mientras se carga
                            .diskCacheStrategy(DiskCacheStrategy.NONE) // Desactivo la caché
                            .skipMemoryCache(true)
                            .error(R.drawable.perfil_user)
                            .into(fotoPerfil);
            }
        });

        // Observador para el botón
        vmPerfil.getButton().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String buttonText) {
                bindingPerfil.btPerfil.setText(buttonText);
            }
        });

        // Observador para el modo de edición
        vmPerfil.getEditar().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isEditing) {
                vmPerfil.cambioEditText(bindingPerfil.navPerfil);
            }
        });

        // cambios entre editar y guardar
        bindingPerfil.btPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //del propietario actual
                Propietario propietarioActual = vmPerfil.getMPropietario().getValue();

                   //capturo el email
                    String email = propietarioActual.getEmail();
                    Log.d("Email Actual", "Email: " + email);
                    vmPerfil.setEmail(email);

                Propietario p = new Propietario();

                //lo seteo al nuevo propietario
                p.setEmail(email);
                p.setApellido(etApellido.getText().toString());
                p.setDni(etDni.getText().toString());
                p.setNombre(etNombre.getText().toString());
                p.setTelefono(etTelefono.getText().toString());
                p.setAvatar(p.getAvatar());
                vmPerfil.cambioBoton();
                vmPerfil.update(p);
            }
        });


        bindingPerfil.btPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Modificar Password", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.btPass).show();
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);

                navController.navigate(R.id.modificarContraseniaFragment);
            }
        });


        bindingPerfil.btFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cuadro de diálogo
                new AlertDialog.Builder(requireContext())
                        .setTitle("Seleccione una opción")
                        .setItems(new CharSequence[]{"Tomar foto", "Elegir de la galería", "Eliminar foto"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        permisosDeCamara();
                                        break;
                                    case 1:
                                        cargarImagen();
                                        break;
                                    case 2:
                                        eliminarFoto();
                                        break;
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        return bindingPerfil.getRoot();
    }

    private void eliminarFoto() {
        vmPerfil.eliminarAvatar();
        vmPerfil.getAvatar().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
    }

    private void permisosDeCamara() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        } else {
            tomarFoto();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tomarFoto(); // Permiso otorgado, abre la cámara
            } else {
                Toast.makeText(getContext(), "Se requiere permiso para usar la cámara", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void tomarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 20); // El requestCode 20 se usa para identificar la respuesta de la cámara
    }


    private Uri getImageUri(Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    //para abrir a galeria
    public void cargarImagen(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);  //intent para seleccionar elemento de la lista(imagen)
        intent.setType("image/");//filtra para mostrar sólo imagenes
        Bundle numero = new Bundle();
        numero.putInt("id",10);//id de solicitud que se usa en onActivityResult
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion..."),10);

    }

    //para cargar la imagen en el imageView
    private void auxImageUri(Uri uri) {
        try {
            InputStream inputStream = getContext().getContentResolver().openInputStream(uri);//abre flujo de entrada a partir de la URI de la imagen
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);//decodifica la imagen al formato Bitmap
            fotoPerfil.setImageBitmap(bitmap);//carga la imagen en el imageView
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null) {//verifica el id de la solicitud que sea el mismo que cuando inicio y que la activity se completo
            Uri path = data.getData();// Obtengo la URI de la imagen
            if (path != null) {
                auxImageUri(path);// Carga la imagen en el ImageView
                vmPerfil.subirAvatar(path, requireContext());//subo la imagen
            }
        } else if (requestCode == 20 && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Uri uri = getImageUri(bitmap);
            auxImageUri(uri);
            vmPerfil.subirAvatar(uri, requireContext());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmPerfil = new ViewModelProvider(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }

}