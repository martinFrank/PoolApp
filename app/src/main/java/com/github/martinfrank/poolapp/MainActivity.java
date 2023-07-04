package com.github.martinfrank.poolapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.toolbox.Volley;
import com.github.martinfrank.poolapp.data.Activator;
import com.github.martinfrank.poolapp.data.Oxygen;
import com.github.martinfrank.poolapp.data.PhChange;
import com.github.martinfrank.poolapp.data.PoolData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements InputChangeListener, RestDataReceiver {

    private static final String LOG_TAG = "com.github.martinfrank.poolapp.MainActivity";
    private ValueStore valueStore;
    private EditorTextWatcher editorTextWatcher;

    @SuppressLint("SimpleDateFormat") //app is used in a local network only - we all share the same time
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final float POOL_DIAMETER = 3.05f;
    private VolleyHandler volleyHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valueStore = new ValueStore(this.getPreferences(MODE_PRIVATE));
        editorTextWatcher = new EditorTextWatcher(this);
        volleyHandler = new VolleyHandler(Volley.newRequestQueue(this), this);
        tweakGui();
        loadStoredValues();
        calculatePoolVolume();
        createEditorListeners();
        createTableListeners();
        createDummyValues();
        updateDate();
        volleyHandler.loadPoolData();

    }

    private void tweakGui() {
        ((EditText)findViewById(R.id.editTextDate)).setSingleLine(true);
        ((EditText)findViewById(R.id.editTextHeight)).setSingleLine(true);
        ((EditText)findViewById(R.id.editTextTemperature)).setSingleLine(true);
        ((EditText)findViewById(R.id.editTextPh)).setSingleLine(true);
        ((EditText)findViewById(R.id.editTextPhChanger)).setSingleLine(true);
        ((EditText)findViewById(R.id.editTextOxygen)).setSingleLine(true);
        ((EditText)findViewById(R.id.editTextActivator)).setSingleLine(true);
    }

    private void createTableListeners() {
        findViewById(R.id.refresh).setOnClickListener(view -> volleyHandler.loadPoolData());
    }


    private void createEditorListeners() {
        Button updateButton = findViewById(R.id.update);
        updateButton.setOnClickListener(view -> insertOrUpdate());

        Button deleteButton = findViewById(R.id.delete);
        deleteButton.setOnClickListener(view -> deletePoolData());

        Button todayButton = findViewById(R.id.today);
        todayButton.setOnClickListener(view -> updateDate());

        EditText heightEditText = findViewById(R.id.editTextHeight);
        heightEditText.addTextChangedListener(editorTextWatcher);

        EditText phEditText = findViewById(R.id.editTextPh);
        phEditText.addTextChangedListener(editorTextWatcher);

        disableEditText(R.id.editTextVolume);

    }

    private void deletePoolData() {
        Log.d(LOG_TAG, "delete");
        PoolData poolData = readEditorContent();
        volleyHandler.delete(poolData);
    }

    private void insertOrUpdate() {
        Log.d(LOG_TAG, "update");
        PoolData poolData = readEditorContent();
        volleyHandler.insertOrUpdate(poolData);
    }

    private void updateDate() {
        Log.d(LOG_TAG, "update date");
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        ((EditText) findViewById(R.id.editTextDate)).setText(DATE_FORMAT.format(calendar.getTime()));
    }

    private void disableEditText(int editTextId) {
        EditText editText = findViewById(editTextId);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(true);
    }

    private void loadStoredValues() {
        Log.d(LOG_TAG, "loadStoredValues()");
        ((EditText) findViewById(R.id.editTextHeight)).setText(valueStore.loadHeight() );
    }

    private void saveStoredValues() {
        Log.d(LOG_TAG, "saveStoredValues()");
        valueStore.storeHeight(Float.toString(getValueFromText(findViewById(R.id.editTextHeight))));
    }

    private void calculatePoolVolume() {
        float height = getValueFromText(findViewById(R.id.editTextHeight));
        float radius = POOL_DIAMETER / 2f;
        float volume = (radius * radius) * ((float) Math.PI) * height;
        ((EditText) findViewById(R.id.editTextVolume)).setText(prettyNumber("" + volume));
    }

    private float getValueFromText(EditText editText) {
        try {
            return (float) Double.parseDouble("" + editText.getText());
        } catch (NumberFormatException | NullPointerException e) {
            return 0f;
        }
    }

    private void createDummyValues() {
        TableLayout table = findViewById(R.id.pool_data_table);
        TableLayout tableRow = (TableLayout) getLayoutInflater().inflate(R.layout.pool_data_row, table);
        getLayoutInflater().inflate(R.layout.pool_data_row, table);
        getLayoutInflater().inflate(R.layout.pool_data_row, table);
        getLayoutInflater().inflate(R.layout.pool_data_row, table);
    }


    public void rowClick(View view) {
        Log.d(LOG_TAG, "row click");
        PoolData poolData = new PoolData();
        poolData.setDate("" + ((TextView) view.findViewById(R.id.textViewDate)).getText());
        poolData.setVolume("" + ((TextView) view.findViewById(R.id.textViewVolume)).getText());
        poolData.setTemperature("" + ((TextView) view.findViewById(R.id.textViewTemperature)).getText());
        poolData.setPh("" + ((TextView) view.findViewById(R.id.textViewPh)).getText());
        poolData.setPhChanger("" + ((TextView) view.findViewById(R.id.textViewPhChanger)).getText());
        poolData.setOxygen("" + ((TextView) view.findViewById(R.id.textViewOxygen)).getText());
        poolData.setActivator("" + ((TextView) view.findViewById(R.id.textViewActivator)).getText());

        transferRowToEditor(poolData);
    }

    private void transferRowToEditor(PoolData poolData) {
        editorTextWatcher.setEnabled(false);
        Log.d(LOG_TAG, "transferRowToEditor, poolData: " + poolData);
        ((EditText) findViewById(R.id.editTextDate)).setText(poolData.getDate());
        ((EditText) findViewById(R.id.editTextVolume)).setText(poolData.getVolume());
        ((EditText) findViewById(R.id.editTextTemperature)).setText(poolData.getTemperature());
        ((EditText) findViewById(R.id.editTextPh)).setText(poolData.getPh());
        ((EditText) findViewById(R.id.editTextPhChanger)).setText(poolData.getPhChanger());
        ((EditText) findViewById(R.id.editTextOxygen)).setText(poolData.getOxygen());
        ((EditText) findViewById(R.id.editTextActivator)).setText(poolData.getActivator());
        editorTextWatcher.setEnabled(true);
    }


    @Override
    public void updatePoolData(List<PoolData> poolDataList) {
        TableLayout table = findViewById(R.id.pool_data_table);
        table.removeAllViewsInLayout();
        for (PoolData poolData : poolDataList) {
            Log.d(LOG_TAG, "processing pooldata: " + poolData);
            TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.pool_data_row, table, false);
            ((TextView) tableRow.findViewById(R.id.textViewDate)).setText(poolData.getDate());
            ((TextView) tableRow.findViewById(R.id.textViewVolume)).setText(poolData.getVolume());
            ((TextView) tableRow.findViewById(R.id.textViewTemperature)).setText(poolData.getTemperature());
            ((TextView) tableRow.findViewById(R.id.textViewPh)).setText(poolData.getPh());
            ((TextView) tableRow.findViewById(R.id.textViewPhChanger)).setText(poolData.getPhChanger());
            ((TextView) tableRow.findViewById(R.id.textViewOxygen)).setText(poolData.getOxygen());
            ((TextView) tableRow.findViewById(R.id.textViewActivator)).setText(poolData.getActivator());
            table.addView(tableRow);
        }

    }

    @Override
    public void updatePhChange(PhChange phChange) {
        Log.d(LOG_TAG, "updatePhChange: " + phChange);
        ((EditText) findViewById(R.id.editTextPhChanger)).setText(prettyNumber(Float.toString(phChange.getGramsToAdd())));
    }

    @Override
    public void updateOxygen(Oxygen oxygen) {
        Log.d(LOG_TAG, "updateOxygen: " + oxygen);
        ((EditText) findViewById(R.id.editTextOxygen)).setText(prettyNumber(oxygen.getGramsToAdd()));
    }

    @Override
    public void updateActivator(Activator activator) {
        Log.d(LOG_TAG, "updateActivator: " + activator);
        ((EditText) findViewById(R.id.editTextActivator)).setText(prettyNumber(activator.getMillilitersToAdd()));
    }

    @Override
    public void showToast(String message){
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void inputChanged() {
        calculatePoolVolume();
        saveStoredValues();
        loadTemplateValues();

    }

    private void loadTemplateValues() {
        float volume = getValueFromText(findViewById(R.id.editTextVolume));
        float ph = getValueFromText(findViewById(R.id.editTextPh));
        volleyHandler.loadPhChangerTemplate(volume, ph);
        volleyHandler.loadOxygenTemplate(volume);
        volleyHandler.loadActivatorTemplate(volume);
    }

    private static String prettyNumber(String raw){
        Log.d(LOG_TAG, "prettyNumber, raw: " + raw);
        float value = Float.parseFloat(raw);
        String result = String.format(Locale.ENGLISH, "%.1f", value);
        Log.d(LOG_TAG, "result: " + result);
        return result;
    }

    private PoolData readEditorContent(){
        PoolData poolData = new PoolData();
        poolData.setDate("" + ((EditText)findViewById(R.id.editTextDate)).getText());
        poolData.setVolume("" + ((EditText)findViewById(R.id.editTextVolume)).getText());
        poolData.setTemperature("" + ((EditText)findViewById(R.id.editTextTemperature)).getText());
        poolData.setPh("" + ((EditText)findViewById(R.id.editTextPh)).getText());
        poolData.setPhChanger("" + ((EditText)findViewById(R.id.editTextPhChanger)).getText());
        poolData.setOxygen("" + ((EditText)findViewById(R.id.editTextOxygen)).getText());
        poolData.setActivator("" + ((EditText)findViewById(R.id.editTextActivator)).getText());
        return poolData;
    }
}