package mz.co.commandline.grocery.files;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import okhttp3.ResponseBody;

public interface FileService {

    void loadPdfFile(String fileName, ResponseListner<ResponseBody> responseListner);

}
