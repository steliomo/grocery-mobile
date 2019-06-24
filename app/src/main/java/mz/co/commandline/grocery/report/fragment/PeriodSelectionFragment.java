package mz.co.commandline.grocery.report.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.report.delegate.ReportDelegate;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;


public class PeriodSelectionFragment extends BaseFragment {

    @BindView(R.id.fragment_period_selection_start_date)
    TextInputLayout startDate;

    @BindView(R.id.fragment_period_selection_end_date)
    TextInputLayout endDate;

    private ReportDelegate delegate;

    private List<Validator> validators;

    @Override
    public int getResourceId() {
        return R.layout.fragment_period_selection;
    }

    @Override
    public void onCreateView() {
        delegate = (ReportDelegate) getActivity();
        validators = new ArrayList<>();

        configureFields(startDate, endDate);
    }

    private void configureFields(TextInputLayout... textInputLayouts) {
        for (TextInputLayout textInputLayout : textInputLayouts) {

            validators.add(new DefaultValidator(textInputLayout));

            final EditText editText = textInputLayout.getEditText();

            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideKeyboard(view);
                    setDate(editText);
                }
            });
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setDate(final EditText editText) {
        Calendar instance = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = StringUtils.leftPad((dayOfMonth) + "", 2, "0") + "-" +
                        StringUtils.leftPad((month + 1) + "", 2, "0") + "-" +
                        year;

                editText.setText(date);
            }
        }, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    @OnClick(R.id.fragment_period_selection_submit_btn)
    public void onSubmit() {

        for (Validator validator : validators) {
            if (!validator.isValid()) {
                return;
            }
        }

        delegate.displaySalesPerPeriodReport(startDate.getEditText().getText().toString(), endDate.getEditText().getText().toString());
    }
}
