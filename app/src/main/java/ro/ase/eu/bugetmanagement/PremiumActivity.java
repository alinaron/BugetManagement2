package ro.ase.eu.bugetmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ro.ase.eu.network.httpManager;

public class PremiumActivity extends AbstractActivity {

    private static final String URL = "https://api.myjson.com/bins/184n06";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);
        httpManager manager=new httpManager(){
            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
        };
        manager.execute(URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.item_premium);
        item.setVisible(false);
        return true;
    }
}
