package com.example.texttospeech;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Locale;
import android.widget.Toast;

public class MainActivity extends Activity {

	/* variables */
	
	TextToSpeech text_to_speech, speech_object;
	EditText txt_value;
	Button btn_speak, btn_numbers;
	Locale loc;
	String[] ar;
	 
	public TextToSpeech speech_object(){
	    /* Speech Object */
	   speech_object = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
	       @Override
	       public void onInit(int status) {
	            if(status != TextToSpeech.ERROR) {
	            }
	       }
	    });
	   return speech_object;
	}
	
    public TextToSpeech set_voice(TextToSpeech object){
    	Spinner spinner_voice=(Spinner) findViewById(R.id.options_language);
    	String voice_value = spinner_voice.getSelectedItem().toString();
    	String locale = "";
    	if (voice_value.equals("Español")){
  		  	loc = new Locale("es", "MEX");
  		  	object.setLanguage(loc);
  		    ar = getResources().getStringArray(R.array.spanish_numbers);
    	} else if (voice_value.equals("Frances")){
  		  	object.setLanguage(Locale.FRANCE);
  		  	ar = getResources().getStringArray(R.array.france_numbers);
    	} else if (voice_value.equals("Portuges")){
  		  	loc = new Locale("pt", "BR");
  		  	object.setLanguage(loc);
  		  	System.out.printf("|",Locale.getAvailableLocales());
    		ar = getResources().getStringArray(R.array.portuges_numbers);
    	}
    	Toast.makeText(getApplicationContext(), locale,Toast.LENGTH_SHORT).show();
    	return object;
    }
	
    public void speak(TextToSpeech object, String val){
    	object.speak(val, TextToSpeech.QUEUE_ADD, null);
    }
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        txt_value = (EditText)findViewById(R.id.txt_value);
        btn_numbers = (Button)findViewById(R.id.btn_numbers);
        btn_speak = (Button)findViewById(R.id.btn_speak);
        
        /* Cargar Spinner */
    	Spinner spinner = (Spinner) findViewById(R.id.options_language);
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options_language , android.R.layout.simple_spinner_item);
    	
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        /* Create speech object */
        text_to_speech = speech_object();
        
        /* Button Listener */
        btn_numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	//String toSpeak = txt_value.getText().toString();
            	text_to_speech = set_voice(text_to_speech);
            	int len = ar.length;
            	for (int i = 0; i < len; i++){
            		speak(text_to_speech,ar[i]);	
            	}
            	//Toast.makeText(getApplicationContext(), "Operación terminada",Toast.LENGTH_SHORT).show();
            }
         });
        
        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	//String toSpeak = txt_value.getText().toString();
            	text_to_speech = set_voice(text_to_speech);
            	speak(text_to_speech,txt_value.getText().toString());
            	//Toast.makeText(getApplicationContext(), "Operación terminada",Toast.LENGTH_SHORT).show();
            }
         });
    }
    
    public void onPause(){
        if(text_to_speech !=null){
        	text_to_speech.stop();
        	text_to_speech.shutdown();
        }
        super.onPause();
     }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}