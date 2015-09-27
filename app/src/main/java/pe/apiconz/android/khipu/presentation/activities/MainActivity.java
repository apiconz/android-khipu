package pe.apiconz.android.khipu.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.apiconz.android.khipu.R;
import pe.apiconz.android.khipu.model.preferences.SharedPreferenceCard;

public class MainActivity extends AppCompatActivity {

    protected
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    protected
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    protected
    @Bind(R.id.list_layout)
    LinearLayout list;

    private EditText description, number;
    private View positiveAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar();
        updateList();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fab)
    protected void addCard() {

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.title_dialog)
                .customView(R.layout.dialog_add, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Map<String, String> map = SharedPreferenceCard.getHashMap();
                        map.put(number.getText().toString(), description.getText().toString());
                        SharedPreferenceCard.setHashMap(map);
                        updateList();
                    }
                }).build();

        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        description = (EditText) dialog.getCustomView().findViewById(R.id.description);
        number = (EditText) dialog.getCustomView().findViewById(R.id.number);
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                positiveAction.setEnabled(s.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        dialog.show();
        positiveAction.setEnabled(false);
    }

    private void setSupportActionBar() {
        setSupportActionBar(toolbar);
        collapsingToolbar.setTitle(getResources().getString(R.string.app_name));
    }

    private void updateList() {
        list.removeAllViews();
        Map<String, String> map = SharedPreferenceCard.getHashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            View item = getLayoutInflater().inflate(R.layout.item_list, null);
            ((TextView) item.findViewById(R.id.description)).setText(entry.getValue());
            ((TextView) item.findViewById(R.id.number)).setText(entry.getKey());
            item.setOnLongClickListener(v -> {
                new MaterialDialog.Builder(this)
                        .items(R.array.options)
                        .itemsCallback((materialDialog, view, i, charSequence) -> {

                            switch (i) {
                                case 0:
                                    updateCard(entry);
                                    break;
                                case 1:
                                    Map<String, String> dialogMap = SharedPreferenceCard.getHashMap();
                                    dialogMap.remove(entry.getKey());
                                    SharedPreferenceCard.setHashMap(dialogMap);
                                    updateList();
                                    break;
                            }
                        }).show();
                return true;
            });

            item.setOnClickListener(v -> {

                Intent intent = new Intent(getBaseContext(), CodeActivity.class).putExtra(Intent.EXTRA_TEXT, String.valueOf(entry.getKey()));
                startActivity(intent);
            });

            list.addView(item);
        }
    }

    private void updateCard(Map.Entry<String, String> entry) {

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.title_dialog)
                .customView(R.layout.dialog_add, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Map<String, String> map = SharedPreferenceCard.getHashMap();
                        map.put(number.getText().toString(), description.getText().toString());
                        SharedPreferenceCard.setHashMap(map);
                        updateList();
                    }
                }).build();


        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        description = (EditText) dialog.getCustomView().findViewById(R.id.description);
        number = (EditText) dialog.getCustomView().findViewById(R.id.number);
        description.setText(entry.getValue());
        number.setEnabled(false);
        number.setText(entry.getKey());
        dialog.show();
    }

}
