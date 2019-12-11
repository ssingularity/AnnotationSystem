package cn.sjtu.project.ui.activity;

import android.net.Uri;

import com.pdftron.common.PDFNetException;
import com.pdftron.pdf.PDFDoc;
import com.pdftron.pdf.PDFViewCtrl;
import com.pdftron.pdf.utils.AppUtils;
import com.pdftron.pdf.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import cn.sjtu.project.R;
import cn.sjtu.project.common.MyActivity;

public class PDFActivity extends MyActivity {
    @BindView(R.id.pdfviewctrl)
    PDFViewCtrl mPdfViewCtrl;

    PDFDoc mPdfDoc;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pdf;
    }

    @Override
    protected void initView() {
        try {
            AppUtils.setupPDFViewCtrl(mPdfViewCtrl);
        } catch (PDFNetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {
        try {
            File file = Utils.copyResourceToLocal(this, R.raw.sample, "sample", ".pdf");
            mPdfDoc = new PDFDoc(file.getAbsolutePath());
            mPdfViewCtrl.setDoc(mPdfDoc);
        } catch (PDFNetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPdfViewCtrl != null) {
            mPdfViewCtrl.pause();
            mPdfViewCtrl.purgeMemory();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPdfViewCtrl != null) {
            mPdfViewCtrl.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPdfViewCtrl != null) {
            mPdfViewCtrl.destroy();
            mPdfViewCtrl = null;
        }

        if (mPdfDoc != null) {
            try {
                mPdfDoc.close();
            } catch (Exception e) {
                // handle exception
            } finally {
                mPdfDoc = null;
            }
        }
    }
}
