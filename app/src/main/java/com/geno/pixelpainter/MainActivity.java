package com.geno.pixelpainter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		GridLayout l = new GridLayout(this);
		l.setColumnCount(16);
		setContentView(l);
		for (int i = 0; i < 256; i++)
		{
			ImageView v = new ImageView(this);
			v.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					ColorPicker2(MainActivity.this, 0, v);
				}
			});
			l.addView(v);
		}
	}

	public static void ColorPicker2(Context context, int originColor, final View view)
	{
		LinearLayout l = new LinearLayout(context);
		l.setOrientation(LinearLayout.VERTICAL);
		final Button confirm = new Button(context);
		confirm.setText(android.R.string.ok);
		confirm.setBackgroundColor(originColor);
		confirm.setPadding(10, 10, 10, 10);
		Button cancel = new Button(context);
		cancel.setText(android.R.string.cancel);
		cancel.setBackgroundColor(originColor);
		cancel.setPadding(10, 10, 10, 10);
		TextView text_a = new TextView(context);
		text_a.setText("Alpha");
		text_a.setPadding(10, 10, 10, 10);
		TextView text_r = new TextView(context);
		text_r.setText("Red");
		text_r.setPadding(10, 10, 10, 10);
		TextView text_g = new TextView(context);
		text_g.setText("Green");
		text_g.setPadding(10, 10, 10, 10);
		TextView text_b = new TextView(context);
		text_b.setText("Blue");
		text_b.setPadding(10, 10, 10, 10);
		final SeekBar color_A = new SeekBar(context);
		color_A.setMax(255);
		color_A.setProgress(originColor >> 24);
		final SeekBar color_R = new SeekBar(context);
		color_R.setMax(255);
		color_R.setProgress(originColor << 8 >> 24);
		final SeekBar color_G = new SeekBar(context);
		color_G.setMax(255);
		color_G.setProgress(originColor << 16 >> 24);
		final SeekBar color_B = new SeekBar(context);
		color_B.setMax(255);
		color_B.setProgress(originColor << 24 >> 24);
		final GradientDrawable gd_a = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{originColor & 0x00FFFFFF, originColor | 0xFF000000});
		color_A.setProgressDrawable(gd_a);
		final GradientDrawable gd_r = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{originColor & 0xFF00FFFF, originColor | 0x00FF0000});
		color_R.setProgressDrawable(gd_r);
		final GradientDrawable gd_g = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{originColor & 0xFFFF00FF, originColor | 0x0000FF00});
		color_G.setProgressDrawable(gd_g);
		final GradientDrawable gd_b = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{originColor & 0xFFFFFF00, originColor | 0x000000FF});
		color_B.setProgressDrawable(gd_b);
		SeekBar.OnSeekBarChangeListener c = new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				int a = color_A.getProgress() << 24;
				int r = color_R.getProgress() << 16;
				int g = color_G.getProgress() << 8;
				int b = color_B.getProgress();
				gd_a.setColors(new int[]{  + r + g + b, 0xFF000000 + r + g + b});
				gd_r.setColors(new int[]{a +   + g + b, a + 0x00FF0000 + g + b});
				gd_g.setColors(new int[]{a + r +   + b, a + r + 0x0000FF00 + b});
				gd_b.setColors(new int[]{a + r + g    , a + r + g + 0x000000FF});
				confirm.setBackgroundColor(a + r + g + b);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar)
			{

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar)
			{

			}
		};
		color_A.setOnSeekBarChangeListener(c);
		color_R.setOnSeekBarChangeListener(c);
		color_G.setOnSeekBarChangeListener(c);
		color_B.setOnSeekBarChangeListener(c);
		l.addView(text_a);
		l.addView(color_A, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		l.addView(text_r);
		l.addView(color_R, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		l.addView(text_g);
		l.addView(color_G, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		l.addView(text_b);
		l.addView(color_B, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		final Dialog d;
		AlertDialog.Builder a = new AlertDialog.Builder(context).setView(l);
		d = a.create();
		confirm.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				view.setBackgroundColor(Color.argb(color_A.getProgress(), color_R.getProgress(), color_G.getProgress(), color_B.getProgress()));
				d.dismiss();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				d.dismiss();
			}
		});
		l.addView(cancel);
		l.addView(confirm);
		d.show();
	}
}
