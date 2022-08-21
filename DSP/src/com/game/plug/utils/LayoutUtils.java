package com.game.plug.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.plug.ui.FlikerProgressBar;

public class LayoutUtils {

	
	@SuppressWarnings("deprecation")
	public static final View initUI(Context context,Drawable drawable1,Drawable drawable2){
		
		RelativeLayout mainLayout=new RelativeLayout(context);
		
		mainLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		mainLayout.setGravity(Gravity.CENTER);
		mainLayout.setBackgroundColor(0x00000000);
		
		RelativeLayout.LayoutParams layoutParamsRoot =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,dip2px(context, 280));
		
		RelativeLayout layoutRoot = new RelativeLayout(context);      
		layoutRoot.setLayoutParams(layoutParamsRoot);
		layoutRoot.setGravity(Gravity.CENTER);
		layoutRoot.setBackgroundDrawable(drawable1);
		
		RelativeLayout.LayoutParams textViewParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int leftMargin1 = dip2px(context, 20);  
		textViewParams.leftMargin=leftMargin1;
		textViewParams.topMargin=dip2px(context, 30);
		textViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		TextView textView=new TextView(context);
		textView.setLayoutParams(textViewParams);
		textView.setTextColor(Color.BLACK);
		textView.setText("版本更新");
		textView.setTextSize(22);
		int textViewID = 100;
		textView.setId(textViewID);
		
		layoutRoot.addView(textView);
		
		RelativeLayout.LayoutParams layoutParamsThird=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParamsThird.addRule(RelativeLayout.BELOW, textViewID);
        int leftMargin2 = dip2px(context, 30);  
        int rightMargin2 = dip2px(context, 30); 
        int topMargin2 = dip2px(context, 20); 
         
        layoutParamsThird.leftMargin=leftMargin2;
        layoutParamsThird.rightMargin=rightMargin2;
        layoutParamsThird.topMargin=topMargin2;
        
        LinearLayout layoutThird=new LinearLayout(context);
        layoutThird.setLayoutParams(layoutParamsThird);
        layoutThird.setOrientation(LinearLayout.VERTICAL);
        int linearLayoutID=200;
        layoutThird.setId(linearLayoutID);
        LinearLayout.LayoutParams layoutParamsThirdF=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
		
        LinearLayout layoutThirdF=new LinearLayout(context);
        layoutThirdF.setLayoutParams(layoutParamsThirdF);
        layoutThirdF.setOrientation(LinearLayout.HORIZONTAL);
        
        LinearLayout.LayoutParams textNumberFParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        
        TextView textNumberF=new TextView(context);
        textNumberF.setLayoutParams(textNumberFParams);
        textNumberF.setText("1.");
        textNumberF.setTextColor(0xff555353);
        
        layoutThirdF.addView(textNumberF);
        
        LinearLayout.LayoutParams textTitleFParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textTitleF=new TextView(context);
        textTitleF.setLayoutParams(textTitleFParams);
        textTitleF.setText("");
        textTitleF.setTextColor(0xff555353);
        textTitleF.setTextSize(14);
        int textFirstId=1301;
        textTitleF.setId(textFirstId);
        
        layoutThirdF.addView(textTitleF);
        layoutThird.addView(layoutThirdF);
        
        LinearLayout.LayoutParams viewParamsF=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        
        View viewF=new View(context);
        viewF.setLayoutParams(viewParamsF);
        viewF.setBackgroundColor(0xffffff);
        layoutThird.addView(viewF);
        
        LinearLayout.LayoutParams layoutParamsThirdS=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
		
        LinearLayout layoutThirdS=new LinearLayout(context);
        layoutThirdS.setLayoutParams(layoutParamsThirdS);
        layoutThirdS.setOrientation(LinearLayout.HORIZONTAL);
        
        LinearLayout.LayoutParams textNumberSParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        
        TextView textNumberS=new TextView(context);
        textNumberS.setLayoutParams(textNumberSParams);
        textNumberS.setText("2.");
        textNumberS.setTextColor(0xff555353);
        
        layoutThirdS.addView(textNumberS);
        
        LinearLayout.LayoutParams textTitleSParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textTitleS=new TextView(context);
        textTitleS.setLayoutParams(textTitleSParams);
        textTitleS.setText("");
        textTitleS.setTextColor(0xff555353);
        textTitleS.setTextSize(14);
        int textSecondId=1302;
        textTitleS.setId(textSecondId);
        
        layoutThirdS.addView(textTitleS);
        layoutThird.addView(layoutThirdS);
        
        LinearLayout.LayoutParams viewParamsS=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        
        View viewS=new View(context);
        viewS.setLayoutParams(viewParamsS);
        viewS.setBackgroundColor(0xffffff);
        layoutThird.addView(viewS);
        LinearLayout.LayoutParams layoutParamsThirdT=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
		
        LinearLayout layoutThirdT=new LinearLayout(context);
        layoutThirdT.setLayoutParams(layoutParamsThirdT);
        layoutThirdT.setOrientation(LinearLayout.HORIZONTAL);
        
        LinearLayout.LayoutParams textNumberTParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        
        TextView textNumberT=new TextView(context);
        textNumberT.setLayoutParams(textNumberTParams);
        textNumberT.setText("3.");
        textNumberT.setTextColor(0xff555353);
        
        layoutThirdT.addView(textNumberT);
        
        LinearLayout.LayoutParams textTitleTParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textTitleT=new TextView(context);
        textTitleT.setLayoutParams(textTitleTParams);
        textTitleT.setText("");
        textTitleT.setTextColor(0xff555353);
        textTitleT.setTextSize(14);
        int textThirdId=1303;
        textTitleT.setId(textThirdId);
        
        layoutThirdT.addView(textTitleT);
        layoutThird.addView(layoutThirdT);
        
        RelativeLayout.LayoutParams layoutParamsSecond=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,px2dip(context, 8));
        layoutParamsSecond.addRule(RelativeLayout.BELOW, linearLayoutID);
        int topMargin3 = dip2px(context, 20);
        layoutParamsSecond.topMargin=topMargin3;
        
        TextView textViewSecond=new TextView(context);
        textViewSecond.setLayoutParams(layoutParamsSecond);
        textViewSecond.setBackgroundColor(0xffefefef);
        int textLineID=300;
        textViewSecond.setId(textLineID);
        layoutRoot.addView(textViewSecond);

        RelativeLayout.LayoutParams layoutParamsLast=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParamsLast.addRule(RelativeLayout.BELOW, textLineID);
        layoutParamsLast.addRule(RelativeLayout.CENTER_IN_PARENT);
        RelativeLayout layoutLast=new RelativeLayout(context);
        
        
        RelativeLayout.LayoutParams progressParamsSecond=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int leftMargin4 = dip2px(context, 20);  
        int rightMargin4 = dip2px(context, 20); 
        
        progressParamsSecond.leftMargin=leftMargin4;
        progressParamsSecond.rightMargin=rightMargin4;
        progressParamsSecond.addRule(RelativeLayout.CENTER_IN_PARENT);
        
        BitmapDrawable able=(BitmapDrawable)drawable2;
        Bitmap map=able.getBitmap();
        
        FlikerProgressBar progressBar=new FlikerProgressBar(context,map);
        int progressBarId=400;
        progressBar.setId(progressBarId);
        layoutLast.addView(progressBar, progressParamsSecond);
        layoutRoot.addView(layoutLast, layoutParamsLast);
        layoutRoot.addView(layoutThird);
		mainLayout.addView(layoutRoot);
		
		return mainLayout;
	}
	
	
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    } 

	@SuppressWarnings("deprecation")
	public static View initBackground(Context context,Drawable drawable){
				RelativeLayout mainLayout=new RelativeLayout(context);
				
				mainLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
				mainLayout.setGravity(Gravity.CENTER);
				mainLayout.setBackgroundColor(0xff000000);
				mainLayout.setBackgroundColor(0xff000000);
				mainLayout.setBackgroundDrawable(drawable);
				
				return mainLayout;
	}
	
}
