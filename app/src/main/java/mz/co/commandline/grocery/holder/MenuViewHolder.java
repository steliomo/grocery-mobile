package mz.co.commandline.grocery.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.menu.MenuItem;

public class MenuViewHolder extends BaseViewHolder<MenuItem> {

    @BindView(R.id.main_menu_image_icon)
    ImageView imageView;

    @BindView(R.id.main_menu_title)
    TextView textView;

    public MenuViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(MenuItem menuItem) {
        imageView.setImageResource(menuItem.getIconId());
        textView.setText(menuItem.getTitle());
    }
}
