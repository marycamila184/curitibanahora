package br.com.curitibanahora.utils;

import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.widget.ImageView;

import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.LoaderSettings.SettingsBuilder;
import com.novoda.imageloader.core.cache.LruBitmapCache;
import com.novoda.imageloader.core.model.ImageTagFactory;

public class Utils {
	public static void CopyStream(InputStream is, OutputStream os)
	{
		final int buffer_size=1024;
		try
		{
			byte[] bytes=new byte[buffer_size];
			for(;;)
			{
				int count=is.read(bytes, 0, buffer_size);
				if(count==-1)
					break;
				os.write(bytes, 0, count);
			}
		}
		catch(Exception ex){}
	}

	

	public static void loadImageWithPlaceHolder(ImageView view, String url, Context ctxt, int placeholder) {

		ImageTagFactory imageTagFactory;
		ImageManager imageManager;

		imageManager = new ImageManager(ctxt, new SettingsBuilder()
		.withCacheManager(new LruBitmapCache(ctxt))
		.build(ctxt));

		imageTagFactory = ImageTagFactory.newInstance(400, 400, placeholder);
		imageTagFactory.setErrorImageId(placeholder);
		imageTagFactory.setSaveThumbnail(true);

		view.setTag(imageTagFactory.build(url, ctxt));
		imageManager.getLoader().load(view);
	}
	
}	

