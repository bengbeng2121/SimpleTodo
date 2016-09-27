package vn.me.simpletodo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by taq on 26/9/2016.
 */

public class ItemsAdapter extends ArrayAdapter<TodoItem> {

    private Context mContext;
    private ArrayList<TodoItem> mItems;

    public ItemsAdapter(Context context, ArrayList<TodoItem> items) {
        super(context, 0, items);
        this.mContext = context;
        this.mItems = items;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TodoItem item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);
        }
        TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);

        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditActivity(item, position);
                // showEditDialog(item, position);
            }
        });

        tvContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mItems.remove(position);
                item.delete();
                notifyDataSetChanged();
                return true;
            }
        });

        tvContent.setText(item.content);
        return convertView;
    }

    private void showEditActivity(TodoItem item, int position) {
        Intent intent = new Intent(mContext, EditItemActivity.class);
        intent.putExtra(GlobalConstants.ITEM, item.content);
        intent.putExtra(GlobalConstants.POSITION, position);
        ((Activity) mContext).startActivityForResult(intent, GlobalConstants.REQUEST_CODE);
    }

    private void showEditDialog(TodoItem item, int position) {
        FragmentManager fm = ((AppCompatActivity) mContext).getSupportFragmentManager();
        EditItemDialogFragment ef = EditItemDialogFragment.newInstance(item.content, position);
        ef.show(fm, "fragment_edit_item");
    }
}
