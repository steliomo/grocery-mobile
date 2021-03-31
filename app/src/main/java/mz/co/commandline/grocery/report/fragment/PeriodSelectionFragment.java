package mz.co.commandline.grocery.report.fragment;

import android.support.design.widget.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.report.delegate.ReportDelegate;
import mz.co.commandline.grocery.validator.DateValidator;
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

        validators.add(new DateValidator(getActivity(), startDate));
        validators.add(new DateValidator(getActivity(), endDate));
    }

    @OnClick(R.id.fragment_period_selection_submit_btn)
    public void onSubmit() {

        for (Validator validator : validators) {
            if (!validator.isValid()) {
                return;
            }
        }

        delegate.displayReport(startDate.getEditText().getText().toString(), endDate.getEditText().getText().toString());
    }

    @Override
    public String getTitle() {
        return getString(R.string.period);
    }
}
