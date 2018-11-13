package ro.ase.eu.bugetmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.ase.eu.util.Constants;
import ro.ase.eu.util.Expense;
import ro.ase.eu.util.ExpenseAdapter;

public class MainActivity extends AbstractActivity {

    private TextView tvMessage;
    private FloatingActionButton fabAddExpense;
    private ListView lvExpenses;

    private static final String SELECTED_LABEL_KEY = "selectedLabelKey";

    private int selectedTextColorIndex = -1;

    List<Expense> expenses= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

        if (savedInstanceState != null) {
            Log.i("MainActivity", "selected color value " + selectedTextColorIndex);
            tvMessage.setText(savedInstanceState.getString(SELECTED_LABEL_KEY));
        }
    }

    private void initComponents() {
        tvMessage = findViewById(R.id.tv_message);
        fabAddExpense = findViewById(R.id.main_fab_add_expense);
        lvExpenses = findViewById(R.id.main_lv_expenses);

//        ArrayAdapter<Expense> adapter = new ArrayAdapter<>(getApplication(),
//                android.R.layout.simple_list_item_1, expenses);
        ExpenseAdapter adapter = new ExpenseAdapter(getApplicationContext(),
                R.layout.lv_expenses_row, expenses, getLayoutInflater());

        lvExpenses.setAdapter(adapter);


        fabAddExpense.setOnClickListener(addExpenseEvent());
    }

    private View.OnClickListener addExpenseEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        NewExpenseActivity.class);
                //startActivity(intent);
                startActivityForResult(intent,
                        Constants.ADD_EXPENSE_REQUEST_CODE);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ADD_EXPENSE_REQUEST_CODE
                && resultCode == RESULT_OK && data != null) {

            Expense expense = data.getParcelableExtra(Constants.ADD_EXPENSE_KEY);
            if(expense!=null){
                Toast.makeText(getApplicationContext(),
                        expense.toString(),
                        Toast.LENGTH_LONG).show();
                expenses.add(expense);
//                ArrayAdapter<Expense> adapter =
//                        (ArrayAdapter<Expense>) lvExpenses.getAdapter();
                ExpenseAdapter adapter = (ExpenseAdapter) lvExpenses.getAdapter();
                adapter.notifyDataSetChanged();
            }

        } else {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.main_result_activity_error),
                    Toast.LENGTH_LONG).show();
        }

    }

    //    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        tvMessage.setText(savedInstanceState.getString(SELECTED_LABEL_KEY));
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.item_home);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        tvMessage.setText(item.getTitle());
        super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SELECTED_LABEL_KEY, tvMessage.getText().toString());
        Toast.makeText(getApplicationContext(), R.string.main_save_instance_success_message, Toast.LENGTH_SHORT).show();
    }
}
