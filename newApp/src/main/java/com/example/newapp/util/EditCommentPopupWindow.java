
package com.example.newapp.util;


import com.example.newapp.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

/**
 * All rights Reserved, Designed By GeofferySun 
 * @Title: 	SelectPicPopupWindow.java 
 * @Package sun.geoffery.uploadpic 
 * @Description:从底部弹出或滑出选择菜单或窗口
 * @author:	GeofferySun   
 * @date:	2015年1月15日 上午1:21:01 
 * @version	V1.0
 */
public class EditCommentPopupWindow extends PopupWindow {

	public Button getEdit_comment_popupwindow_button() {
		return edit_comment_popupwindow_button;
	}

	public void setEdit_comment_popupwindow_button(
			Button edit_comment_popupwindow_button) {
		this.edit_comment_popupwindow_button = edit_comment_popupwindow_button;
	}

	public EditText getEdit_comment_popupwindow_edittext() {
		return edit_comment_popupwindow_edittext;
	}


	public void setEdit_comment_popupwindow_edittext(
			EditText edit_comment_popupwindow_edittext) {
		this.edit_comment_popupwindow_edittext = edit_comment_popupwindow_edittext;
	}



	private Button edit_comment_popupwindow_button;
	private EditText edit_comment_popupwindow_edittext;
	private View mMenuView;
    private InputMethodManager imm;
	
	
	@SuppressLint("InflateParams")
	public EditCommentPopupWindow(Context context, OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.edit_comment_popupwindow_layout, null);
		edit_comment_popupwindow_edittext = (EditText) mMenuView.findViewById(R.id.edit_comment_popupwindow_edittext);
		edit_comment_popupwindow_button = (Button) mMenuView.findViewById(R.id.edit_comment_popupwindow_button);
		imm=(InputMethodManager)edit_comment_popupwindow_edittext.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		edit_comment_popupwindow_edittext.requestFocus();
		imm.showSoftInput(edit_comment_popupwindow_edittext, 0);
		// 设置按钮监听
		edit_comment_popupwindow_button.setOnClickListener(itemsOnClick);
		edit_comment_popupwindow_edittext.getText().toString();
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.PopupAnimation);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x80000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {

			@Override
			@SuppressLint("ClickableViewAccessibility")
			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.EditComment_RelativeLayout_scrollView).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
						imm.hideSoftInputFromInputMethod(edit_comment_popupwindow_edittext.getWindowToken(), 0);
					}
				}
				return true;
			}
		});

	}

}