package com.softannate.appinmobiliaria.ui.inmuebles;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.softannate.appinmobiliaria.R;
import com.softannate.appinmobiliaria.databinding.FragmentNuevoInmuebleBinding;
import com.softannate.appinmobiliaria.modelos.Inmueble;
import com.softannate.appinmobiliaria.modelos.Tipo;
import com.softannate.appinmobiliaria.modelos.UsoInmueble;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

public class NuevoInmuebleFragment extends Fragment {

    private EditText etDireccion, etPrecio, etSuperficie,etLatitud, etLongitud, etAmbientes;
    private int usoId=-1 , tipoId;
    private ImageView fotoInmueble;
    private Spinner spUso;
    private AutoCompleteTextView acTipo;
    private NuevoInmuebleViewModel vmInmueble;
    private FragmentNuevoInmuebleBinding bindingIN;
    private static int REQUEST_IMAGE_CAPTURE=1;
    private Uri imageUri;

    public static NuevoInmuebleFragment newInstance() {
        return new NuevoInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bindingIN=FragmentNuevoInmuebleBinding.inflate(getLayoutInflater());
        vmInmueble = new ViewModelProvider(this).get(NuevoInmuebleViewModel.class);

        bindingIN.btFotoInmuebleNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creo cuadro de dialogo
                new AlertDialog.Builder(requireContext())
                        .setTitle("Seleccione una opción")
                        .setItems(new CharSequence[]{"Tomar foto", "Elegir de la galería"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        permisosDeCamara();
                                        break;
                                    case 1:
                                        cargarImagen();
                                        break;
                                }
                            }
                        }).setNegativeButton("Cancelar", null).show();               ;
            }
        });
        etLatitud = bindingIN.etLatitud;
        etLongitud = bindingIN.etLongitud;

        etLatitud.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String latitud = s.toString();
                vmInmueble.validarLatitud(latitud);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etLongitud.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String longitud = s.toString();
                vmInmueble.validarLongitud(longitud);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        crearInmueble();
        return bindingIN.getRoot();
    }

    private void crearInmueble(){
        //inicializo campos
        etAmbientes = bindingIN.etAmbientes;
        etDireccion = bindingIN.etDire;

        etPrecio = bindingIN.etPrecio;
        etSuperficie = bindingIN.etSup;
        acTipo = bindingIN.acTipoI;
        spUso = bindingIN.spUso;
        fotoInmueble = bindingIN.imagenInmuebleNuevo;

        //para cargar las opciones al usoInmueble
        vmInmueble.getUsoInmueble().observe(getViewLifecycleOwner(), new Observer<List<UsoInmueble>>() {
            @Override
            public void onChanged(List<UsoInmueble> usos) {
                ArrayAdapter<UsoInmueble> adapterUso= new ArrayAdapter<>(getContext(), R.layout.spinner_item, usos);
                adapterUso.setDropDownViewResource(R.layout.spinner_item);
                spUso.setAdapter(adapterUso);
            }
        });

        //para obtener el id del uso
        spUso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtengo el Uso
                UsoInmueble usoSeleccionado = (UsoInmueble) parent.getItemAtPosition(position);

                // Uso el "uso" para obtener el ID
                vmInmueble.setUsoSeleccionado(position);

                // Verifico el ID
                usoId = vmInmueble.getUsoInmuebleId().getValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //para cargar las opciones de tipo
        vmInmueble.getTipoList().observe(getViewLifecycleOwner(), new Observer<List<Tipo>>() {
            @Override
            public void onChanged(List<Tipo> tipos) {
                ArrayAdapter<Tipo> adapterTipo = new ArrayAdapter<>(getContext(), R.layout.auto_complete_text_view, tipos);
                adapterTipo.setDropDownViewResource(R.layout.auto_complete_text_view);
                acTipo.setAdapter(adapterTipo);
            }
        });

        //para obtener el id del tipo
        acTipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtengo el Tipo
                Tipo tipoSeleccionado = (Tipo) parent.getItemAtPosition(position);

                // Uso el tipo para obtener el ID
                vmInmueble.setTipoSeleccionado(tipoSeleccionado.getId());

                // Verifico el ID
                tipoId = vmInmueble.getTipoId().getValue();
                Log.d("tipoId144", String.valueOf(tipoId));
            }
        });

        bindingIN.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Inmueble inmueble = new Inmueble();

                    inmueble.setUsoInmuebleId(usoId);
                    inmueble.setTipoId(tipoId-1);
                    inmueble.setAmbientes(Integer.parseInt(String.valueOf(etAmbientes.getText())));
                    inmueble.setDireccion(String.valueOf(etDireccion.getText()));
                    inmueble.setLatitud(new BigDecimal(String.valueOf(etLatitud.getText().toString())));
                    inmueble.setLongitud(new BigDecimal(String.valueOf(etLongitud.getText().toString())));
                    inmueble.setPrecio(new BigDecimal(String.valueOf(etPrecio.getText())));
                    inmueble.setSuperficie(new BigDecimal(String.valueOf(etSuperficie.getText())));
                    Log.d("inmueble 2", String.valueOf(inmueble).toString());
                Log.d("inmueble 2", inmueble.toString());

                vmInmueble.nuevoInmueble(inmueble, imageUri);
            }
        });

        // Observo el LiveData para cerrar el fragmento
        vmInmueble.getCierroActivity().observe(getViewLifecycleOwner(), event -> {
            requireActivity().getSupportFragmentManager().popBackStack(); // Cerrar el Fragmento
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

    //para abrir galeria
    public void cargarImagen(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);  //intent para seleccionar elemento de la lista(imagen)
        intent.setType("image/*");//filtra para mostrar sólo imagenes
        Bundle numero = new Bundle();
        numero.putInt("id",10);//id de solicitud que se usa en onActivityResult
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion..."),10);

    }

    //para cargar la imagen en el imageView
    private void auxImageUri(Uri uri) {
        try {
            InputStream inputStream = getContext().getContentResolver().openInputStream(uri);//abre flujo de entrada a partir de la URI de la imagen
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);//decodifica la imagen al formato Bitmap
            fotoInmueble.setImageBitmap(bitmap);//carga la imagen en el imageView
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 20) { // Request code para cámara
                // obtengo el bitmap de la imagen capturada
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageUri = getImageUri(bitmap); // etImageUri para obtener la URI
                auxImageUri(imageUri); // Carga la imagen en el ImageView
            } else if (requestCode == 10) { // Request code para galería
                imageUri = data.getData(); // Obtengo la URI de la imagen
                if (imageUri != null) {
                    Log.d("imageUri", "URI obtenida: " + imageUri.toString());
                    auxImageUri(imageUri); // Carga la imagen en el ImageView
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vmInmueble = new ViewModelProvider(this).get(NuevoInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }

}