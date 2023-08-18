package mz.co.commandline.grocery.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.pos.delegate.PosDelegate;
import mz.co.commandline.grocery.pos.fragment.OpenTableFragment;
import mz.co.commandline.grocery.pos.fragment.PosFragment;

public class PosActivity extends BaseAuthActivity implements View.OnClickListener, PosDelegate {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getActivityFrameLayoutId() {
        return R.id.pos_activity_frame_layout;
    }

    @Override
    public void onGroceryCreate(Bundle bundle) {
        setContentView(R.layout.activity_pos);

        toolbar.setTitle(R.string.sales);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(this);

        showFragment(new PosFragment(), Boolean.FALSE);
    }

    @Override
    public void onClick(View view) {
        popBackStack();
    }

    @Override
    public void openTable() {
        showFragment(new OpenTableFragment(), Boolean.TRUE);
    }
}