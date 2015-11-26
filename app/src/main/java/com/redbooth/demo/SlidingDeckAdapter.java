package com.redbooth.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.redbooth.SlidingDeck;
import com.squareup.picasso.Picasso;

public class SlidingDeckAdapter extends ArrayAdapter<SlidingDeckModel> {
    public SlidingDeckAdapter(Context context) {
        super(context, R.layout.sliding_item);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sliding_item, parent, false);
        }
        SlidingDeckModel item = getItem(position);
        ((TextView)view.findViewById(R.id.elementTitle)).setText(item.getTitle());
        ((TextView)view.findViewById(R.id.description)).setText(item.getDescription());
        ((TextView)view.findViewById(R.id.name)).setText(item.getName());
        ImageView avatar = (ImageView)view.findViewById(R.id.avatar);
        Picasso.with(parent.getContext())
                .load(item.getAvatarUri())
                .placeholder(R.mipmap.ic_launcher)
                .transform(new RoundedTransform())
                .into(avatar);
        final View completeView = view.findViewById(R.id.completeCommand);
        completeView.setTag(item);
        completeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SlidingDeck slidingDeck = (SlidingDeck) parent;
                final SlidingDeckModel model = (SlidingDeckModel) view.getTag();
                if (!slidingDeck.isExpanded()) {
                    slidingDeck.swipeForegroundItem(new SlidingDeck.SwipeEventListener() {
                        @Override
                        public void onSwipe(SlidingDeck view) {
                            remove(model);
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    remove(model);
                    notifyDataSetChanged();
                }
            }
        });
        return view;
    }
}
