package com.zb.secondary_market;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zb.secondary_market.fragment.Assortment_Credit_Fragment;
import com.zb.secondary_market.fragment.Assortment_Price_Fragment;
import com.zb.secondary_market.fragment.Assortment_Redu_Fragment;
import com.zb.secondary_market.fragment.Assortment_Variety_Fragment;

public class Type_Detail_Activity extends FragmentActivity implements
		OnClickListener {

	private Assortment_Variety_Fragment assortment_Variety_Fragment;
	private Assortment_Credit_Fragment assortment_Credit_Fragment;
	private Assortment_Price_Fragment assortment_Price_Fragment;
	private Assortment_Redu_Fragment assortment_Redu_Fragment;
	
	private LinearLayout type_detail_top_linearLayout;
	private Button type_detail_top_button;

	private Button type_detail_button_variety, type_detail_button_credit,
			type_detail_button_price, type_detail_button_redu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_type_detail);

		type_detail_top_linearLayout = (LinearLayout) findViewById(R.id.id_type_detail_top_ll);
		type_detail_top_button = (Button) findViewById(R.id.id_type_detail_top_button);
		type_detail_button_variety = (Button) findViewById(R.id.id_type_detail_button_variety);
		type_detail_button_credit = (Button) findViewById(R.id.id_type_detail_button_credit);
		type_detail_button_price = (Button) findViewById(R.id.id_type_detail_button_price);
		type_detail_button_redu = (Button) findViewById(R.id.id_type_detail_button_redu);

		type_detail_top_button.setOnClickListener(this);
		type_detail_button_variety.setOnClickListener(this);
		type_detail_button_credit.setOnClickListener(this);
		type_detail_button_price.setOnClickListener(this);
		type_detail_button_redu.setOnClickListener(this);

		type_detail_button_variety
				.setBackgroundResource(R.drawable.type_bg_focused);

		assortment_Variety_Fragment = new Assortment_Variety_Fragment();
		assortment_Credit_Fragment = new Assortment_Credit_Fragment();
		assortment_Price_Fragment = new Assortment_Price_Fragment();
		assortment_Redu_Fragment = new Assortment_Redu_Fragment();

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.id_type_detail_framelayout, assortment_Variety_Fragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.id_type_detail_top_button:
			finish();
			break;

		case R.id.id_type_detail_button_variety:
			type_detail_button_variety
					.setBackgroundResource(R.drawable.type_bg_focused);
			type_detail_button_credit
					.setBackgroundResource(R.drawable.type_bg_default);
			type_detail_button_price
					.setBackgroundResource(R.drawable.type_bg_default);
			type_detail_button_redu
					.setBackgroundResource(R.drawable.type_bg_default);
			
			FragmentTransaction ft_variety = getSupportFragmentManager().beginTransaction();
			ft_variety.replace(R.id.id_type_detail_framelayout, assortment_Variety_Fragment);
			ft_variety.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft_variety.commit();
			
			break;

		case R.id.id_type_detail_button_credit:
			type_detail_button_credit
					.setBackgroundResource(R.drawable.type_bg_focused);
			type_detail_button_variety
					.setBackgroundResource(R.drawable.type_bg_default);
			type_detail_button_price
					.setBackgroundResource(R.drawable.type_bg_default);
			type_detail_button_redu
					.setBackgroundResource(R.drawable.type_bg_default);
			
			FragmentTransaction ft_credit = getSupportFragmentManager().beginTransaction();
			ft_credit.replace(R.id.id_type_detail_framelayout, assortment_Credit_Fragment);
			ft_credit.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft_credit.commit();
			
			break;

		case R.id.id_type_detail_button_price:
			type_detail_button_price
					.setBackgroundResource(R.drawable.type_bg_focused);
			type_detail_button_credit
					.setBackgroundResource(R.drawable.type_bg_default);
			type_detail_button_variety
					.setBackgroundResource(R.drawable.type_bg_default);
			type_detail_button_redu
					.setBackgroundResource(R.drawable.type_bg_default);
			
			FragmentTransaction ft_price = getSupportFragmentManager().beginTransaction();
			ft_price.replace(R.id.id_type_detail_framelayout, assortment_Price_Fragment);
			ft_price.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft_price.commit();
			break;

		case R.id.id_type_detail_button_redu:
			type_detail_button_redu
					.setBackgroundResource(R.drawable.type_bg_focused);
			type_detail_button_credit
					.setBackgroundResource(R.drawable.type_bg_default);
			type_detail_button_price
					.setBackgroundResource(R.drawable.type_bg_default);
			type_detail_button_variety
					.setBackgroundResource(R.drawable.type_bg_default);
			
			FragmentTransaction ft_redu = getSupportFragmentManager().beginTransaction();
			ft_redu.replace(R.id.id_type_detail_framelayout, assortment_Redu_Fragment);
			ft_redu.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft_redu.commit();
			break;
			
		default:
			break;
		}
	}
}
