package mz.co.commandline.grocery.main.fragment;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.main.delegate.MainDelegate;
import mz.co.commandline.grocery.sale.dto.SalesDTO;


public class DashboardFragment extends BaseFragment {

    @BindView(R.id.fragment_dashboard_web_view)
    WebView webView;

    @Override
    public int getResourceId() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void onCreateView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(Boolean.TRUE);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/dashboard_chart.html");

        MainDelegate delegate = (MainDelegate) getActivity();
        SalesDTO sales = delegate.getSales();

        Gson gson = new Gson();
        final String data = gson.toJson(sales);

        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String weburl) {
                webView.loadUrl("javascript:runGraph(" + data +
                        ")");
            }
        });
    }

    @Override
    public String getTitle() {
        return getString(R.string.dashboard);
    }
}
