package com.example.daily;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;


import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;




public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    Context context;
    ArrayList<RecyclerItem> items;
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;


    public RecyclerAdapter(ArrayList<RecyclerItem> items) {
        this.items = items;

    }


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder holder;
        View view;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo, parent, false);
            holder = new HeaderViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.finish, parent, false);
            holder = new FooterViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_list, parent, false);
            holder = new ItemViewHolder(view);
        }
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
        } else {
            // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.onBind(items.get(position - 1));
        }

    }
    @Override
    public int getItemCount() {
        return items.size()+2;
    }



    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else if (position == items.size() + 1)
            return TYPE_FOOTER;
        else
            return TYPE_ITEM;

    }
    public void additem(RecyclerItem recyclerItem) {
        items.add(recyclerItem);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView list_title;
        CheckBox ck;

        public ItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            list_title = itemView.findViewById(R.id.title);
            ck = itemView.findViewById(R.id.checkBox);

            list_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition() - 1;
                    if (pos != RecyclerView.NO_POSITION) {
                        RecyclerItem item = items.get(pos);
                        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                        View view = LayoutInflater.from(itemView.getContext()).inflate(R.layout.customdialog, null, false);
                        builder.setView(view);
                        Button exit = view.findViewById(R.id.mod_exitbt);
                        Button save = view.findViewById(R.id.mod_bt);
                        EditText title = view.findViewById(R.id.mod_title);
                        EditText input = view.findViewById(R.id.mod_inputtext);
                        save.setText("수정");

                        title.setText(item.getTitle());
                        input.setText(item.getInputtext());


                        final AlertDialog dialog = builder.create();
                        exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();

                            }
                        });
                        save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String title1 = title.getText().toString();
                                String input1 = input.getText().toString();
                                RecyclerItem item = new RecyclerItem(title1, input1);
                                items.set(getAdapterPosition() - 1, item);
                                notifyItemChanged(getAdapterPosition());
                                dialog.dismiss();


                            }
                        });


                        dialog.show();


                    }
                }
            });
        }
        public void onBind(RecyclerItem recyclerItem) {
            list_title.setText(recyclerItem.getTitle());


        }

    }
    class HeaderViewHolder extends RecyclerView.ViewHolder {


        HeaderViewHolder(View headerView) {
            super(headerView);

        }


    }
    class FooterViewHolder extends RecyclerView.ViewHolder {

        FooterViewHolder(View footerView) {
            super(footerView);


        }
    }

}
