package br.com.curitibanahora.adapters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.curitibanahora.object.Article;
import br.com.curitibanahora.utils.ImageLoader;
import br.com.curitibanahora.utils.Utils;
import br.com.curitibanahora.R;


public class ArticleAdapter extends BaseAdapter {

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_MAX_COUNT = 1;
	
	private ArrayList<Article> liste;
	private static LayoutInflater inflater = null;
	private Activity activity;
	public ImageView image;
	public ImageLoader imageLoader;
	
	public ArticleAdapter(Activity a, ArrayList<Article> listearticle) {
		this.activity = a;
		liste = listearticle;
		if (activity != null)
			imageLoader = new ImageLoader(activity.getApplicationContext());
		try {
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		} catch (Exception e) {

		}
	}

	@Override
	public int getCount() {
		if (this.liste != null)
			return this.liste.size();
		else
			return 0;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return TYPE_ITEM;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	}

	public static class ViewHolder {
		public TextView titre;
		public ImageView image;
		public TextView date;
		public ImageView share;

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		int type = getItemViewType(position);

		final Article article = liste.get(position);
		if (article != null) {

			if (convertView == null) {

				holder = new ViewHolder();

				switch (type) {
				case TYPE_ITEM:
					convertView = inflater.inflate(R.layout.item_menu,
							parent, false);
					holder.titre = ((TextView) convertView
							.findViewById(R.id.textView));
					holder.date = ((TextView) convertView
							.findViewById(R.id.textView1));
					holder.image = ((ImageView) convertView
							.findViewById(R.id.imageView1));
					holder.share = ((ImageView) convertView
							.findViewById(R.id.imageView2));
					break;
				}

				convertView.setTag(holder);

			} else
				holder = (ViewHolder) convertView.getTag();

			if (holder != null && type == TYPE_ITEM) {
				holder.titre.setText(article.getTITLE());
				holder.date.setText(article.getPUBDATE());
				if (article.getIMAGE().compareTo("") == 0){
					holder.image.setImageResource(R.drawable.noimagef);
				}else
					Utils.loadImageWithPlaceHolder(holder.image,
							article.getIMAGE(), activity,
							android.R.color.transparent);

				// share action
				holder.share.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
					//	holder.share.
						Intent sharingIntent = new Intent(
								android.content.Intent.ACTION_SEND);

						BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.image
								.getDrawable();
						Bitmap bitmap = bitmapDrawable.getBitmap();

						// Save this bitmap to a file.
						File cacheDir = activity.getExternalCacheDir();
						File downloadingMediaFile = new File(cacheDir,
								"abc.png");
						try {
							FileOutputStream out = new FileOutputStream(
									downloadingMediaFile);
							if (bitmap != null)
								bitmap.compress(Bitmap.CompressFormat.PNG, 100,
										out);
							out.flush();
							out.close();
						} catch (IOException e) {
							e.printStackTrace();
						}

						sharingIntent.setType("image/*");
						sharingIntent.putExtra(Intent.EXTRA_STREAM,
								Uri.parse("file://" + downloadingMediaFile));

						String shareBody = article.getTITLE() + " "
								+ article.getLINK();
						sharingIntent.putExtra(
								android.content.Intent.EXTRA_TEXT, shareBody);

						activity.startActivity(Intent.createChooser(
								sharingIntent, "Share via"));
					}
				});

			}

		}

		return convertView;
	}

}
