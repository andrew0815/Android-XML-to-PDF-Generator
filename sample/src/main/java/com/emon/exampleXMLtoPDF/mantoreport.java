package com.emon.exampleXMLtoPDF;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class mantoreport extends AppCompatActivity {



        Button btncreatepdf;
        LinearLayout PrincipalContainer;
        Bitmap bitmap;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mantoreport);
            btncreatepdf=findViewById(R.id.buttonpdfreport);
            PrincipalContainer=findViewById(R.id.contanreport);


            btncreatepdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bitmap=LoadBitmap(PrincipalContainer,PrincipalContainer.getWidth(),PrincipalContainer.getHeight());
                    createPdf();
                }
            });


        }

        private Bitmap LoadBitmap(View v, int width, int height) {
            Bitmap bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
            Canvas canvas= new Canvas(bitmap);
            v.draw(canvas);
            return bitmap;
        }

        private void createPdf() {
            WindowManager windowManager= (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics=new DisplayMetrics();

            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            float width=displayMetrics.widthPixels;
            float height=displayMetrics.heightPixels;
            int convertWidth=(int)width,convertHeight=(int)height;

            PdfDocument document=new PdfDocument();
            PdfDocument.PageInfo pageInfo=new PdfDocument.PageInfo.Builder(convertWidth,convertHeight,1).create();
            PdfDocument.Page page=document.startPage(pageInfo);
            Canvas canvas=page.getCanvas();
            Paint paint =new Paint();
            canvas.drawPaint(paint);
            bitmap=Bitmap.createScaledBitmap(bitmap,convertWidth,convertHeight, true);
            canvas.drawBitmap(bitmap, 0, 0,  null);

            document.finishPage(page);

            //"target pdf download "
            String targetPdf= "/sdcard/page.pdf";

            File file;
            file =new File(targetPdf);
            try{
                document.writeTo(new FileOutputStream(file));
                Toast.makeText(this, "archivo creado", Toast.LENGTH_SHORT).show();
            }
            catch(IOException e)
            {
                e.printStackTrace();
                Toast.makeText(this, "error no se creo el archivo", Toast.LENGTH_SHORT).show();
            }
        }
}