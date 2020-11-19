package com.halit.studentapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by developer on 10/30/20.
 */

public class MainListAdapter extends ArrayAdapter<StudentVO>{
    Context context;
    ArrayList<StudentVO> datas;
    int resId;

    public MainListAdapter(Context context, int resId, ArrayList<StudentVO> datas){
        super(context,resId);
        this.context=context;
        this.datas=datas;
        this.resId=resId;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(resId, null);

            MainListWrapper wrapper = new MainListWrapper(convertView);
            convertView.setTag(wrapper);
        }

        MainListWrapper wrapper = (MainListWrapper)convertView.getTag();
        ImageView studentImageView=wrapper.studentImageView;
        TextView nameView=wrapper.nameView;
        final ImageView contactView=wrapper.contactView;

        final  StudentVO vo=datas.get(position);
        nameView.setText(vo.name);

        contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vo.phone != null && !vo.phone.equals("")){
                    MyApplication myApplication =(MyApplication)context.getApplicationContext();
                    if (myApplication.callPermission){
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:"+vo.phone));
                        context.startActivity(intent);
                    }else {
                        Toast t= Toast.makeText(context, R.string.permission_error, Toast.LENGTH_SHORT);
                        t.show();
                    }
                }else {
                    Toast t= Toast.makeText(context, R.string.main_list_phone_error, Toast.LENGTH_SHORT);
                    t.show();

                }
            }
        });

        if (vo.photo != null && !vo.photo.equals("")){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize=10;
            Bitmap bitmap = BitmapFactory.decodeFile(vo.photo, options);
            if (bitmap != null){
                studentImageView.setImageBitmap(bitmap);
            }
        }else {
            studentImageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_student_large, null));
        }

        studentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View root = inflater.inflate(R.layout.dialog_student_image, null);
                ImageView imageView = (ImageView)root.findViewById(R.id.dialog_image);
                if (vo.photo != null && !vo.photo.equals(""));
                BitmapFactory.Options options = new  BitmapFactory.Options();
                options.inSampleSize=10;
                Bitmap bitmap = BitmapFactory.decodeFile(vo.photo, options);
                if (bitmap != null){
                    imageView.setImageBitmap(bitmap);
                }else {
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_student_large, null));
                }

                AlertDialog.Builder builder = new  AlertDialog.Builder(context);
                builder.setView(root);
                AlertDialog dialog=builder.create();
                dialog.show();

            }
        });

        return  convertView;
    }
}

