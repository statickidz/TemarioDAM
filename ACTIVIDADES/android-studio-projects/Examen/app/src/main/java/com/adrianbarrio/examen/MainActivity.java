package com.adrianbarrio.examen;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
        AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, View.OnKeyListener {

    public static String[] asignaturas = {"Matemáticas","Ciencias","Literatura","Filosofía","Tecnología"};
    public static int[] notas = {0,0,0,0,0};
    private int notaSeleccionada;

    private ListView lista;
    private LinearLayout notaLinearLayout;
    private EditText notaEditText;

    ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Componentes
        notaLinearLayout = (LinearLayout) findViewById(R.id.notaLinearLayout);
        notaEditText = (EditText) findViewById(R.id.notaEditText);
        lista = (ListView)findViewById(R.id.asignaturasListView);

        //Menú contextual
        registerForContextMenu(lista);

        //Acciones
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.fila, asignaturas);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(this);
        lista.setOnItemLongClickListener(this);
        notaEditText.setOnKeyListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setSelected(true);
        notaSeleccionada = position;
        notaLinearLayout.setVisibility(View.VISIBLE);

        notaEditText.setText(notas[position] + "");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        view.setSelected(true);
        notaSeleccionada = position;
        notaLinearLayout.setVisibility(View.VISIBLE);
        mActionMode = MainActivity.this.startActionMode(mActionModeCallback);

        notaEditText.setText(notas[position] + "");
        return false;
    }


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_nota, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            switch (item.getItemId()) {
                case R.id.sumarBoton:
                    sumarNota(notaSeleccionada);
                    notaEditText.setText(notas[notaSeleccionada] + "");
                    Toast.makeText(getApplicationContext(), "Nota sumada", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.restarBoton:
                    restarNota(notaSeleccionada);
                    notaEditText.setText(notas[notaSeleccionada]+"");
                    Toast.makeText(getApplicationContext(), "Nota restada", Toast.LENGTH_SHORT).show();
                    return true;

                default:
                    return false;
            }

        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater m=getMenuInflater();
        m.inflate(R.menu.menu_nota, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.sumarBoton:
                sumarNota(info.position);
                notaEditText.setText(notas[info.position] + "");
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.nota_sumada), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.restarBoton:
                restarNota(info.position);
                notaEditText.setText(notas[info.position]+"");
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.nota_restada), Toast.LENGTH_SHORT).show();
                return true;

            default:
                return false;
        }

    }

    private void sumarNota(int posicion) {
        if(notas[posicion] >= 10)
            notas[posicion] = 10;
        else
            notas[posicion]++;
    }

    private void restarNota(int posicion) {
        if(notas[posicion] != 0) notas[posicion]--;
    }

    private void cambiarNota(int posicion, int nota) {
        if(notas[posicion] >= 10 || nota >= 10) {
            notas[posicion] = 10;
        } else {
            notas[posicion] = nota;
        }
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        int nuevaNota = 0;
        String notaText = String.valueOf(notaEditText.getText());
        if(!notaText.equals("")) {
            nuevaNota =  Integer.parseInt(notaText);
        }
        cambiarNota(notaSeleccionada, nuevaNota);
        return false;
    }
}
