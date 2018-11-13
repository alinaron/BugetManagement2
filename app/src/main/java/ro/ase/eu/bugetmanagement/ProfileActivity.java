package ro.ase.eu.bugetmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import ro.ase.eu.util.Constants;

public class ProfileActivity extends AbstractActivity {

    private RadioGroup rdSex;
    private TextInputEditText txtFirstName, txtLastName, txtAge;
    private Button btnSave;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.item_profile);
        item.setVisible(false);
        return true;
    }
    private void init()
    {
        txtFirstName=findViewById(R.id.profile_tid_first_name);
        txtLastName=findViewById(R.id.profile_tid_last_name);
        txtAge=findViewById(R.id.profile_tid_age);
        btnSave=findViewById(R.id.profile_btn_save);
        rdSex=findViewById(R.id.profile_rg_gender);

        sharedPreferences=getSharedPreferences(Constants.PROFILE_PREF_FILE_NAME,MODE_PRIVATE);

        btnSave.setOnClickListener(saveEvent());

        restoreSharedPreferences();
    }
    private View.OnClickListener saveEvent()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName=txtFirstName.getText()!=null?txtFirstName.getText().toString():null;
                String lastName=txtLastName.getText()!=null?txtLastName.getText().toString():null;
                Integer age=txtAge.getText()!=null?Integer.parseInt(txtAge.getText().toString()):null;
                Integer genderCheckedButtonId = rdSex.getCheckedRadioButtonId();


                SharedPreferences.Editor editor=sharedPreferences.edit();

                editor.putString(Constants.PROFILE_FIRST_NAME_PREF,firstName);
                editor.putString(Constants.PROFILE_LAST_NAME_PREF,lastName);
                editor.putInt(Constants.PROFILE_AGE_PREF,age);
                editor.putInt(Constants.PROFILE_GENDER_PREF,genderCheckedButtonId);

                boolean result=editor.commit();  //FOARE IMPORTANT!!!
                if (result){
                    Toast.makeText(getApplicationContext(),getString(R.string.profile_result_ok_msg),Toast.LENGTH_SHORT).show();;
                }
                else{
                    Toast.makeText(getApplicationContext(),getString(R.string.profile_result_err_msg),Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void restoreSharedPreferences()
    {

        String firstName=sharedPreferences.getString(Constants.PROFILE_FIRST_NAME_PREF,null);
        String lastName=sharedPreferences.getString(Constants.PROFILE_LAST_NAME_PREF,null);
        Integer age=sharedPreferences.getInt(Constants.PROFILE_AGE_PREF,0);
        Integer checkedRadioButtonGenderId=sharedPreferences.getInt(Constants.PROFILE_GENDER_PREF,R.id.profile_rb_gender_male);//a doua e valoare default pe care o ia in cazul in care nu gaseste in sharedPrefereces

        txtFirstName.setText(firstName);
        txtLastName.setText(lastName);
        txtAge.setText(age>0?age.toString():null);
        rdSex.check(checkedRadioButtonGenderId);
    }
}
