package dz.ubma.bookcollection_.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dz.ubma.bookcollection_.BookDetails;
import dz.ubma.bookcollection_.Information;
import dz.ubma.bookcollection_.R;
import dz.ubma.bookcollection_.mycollection;
import dz.ubma.bookcollection_.recycler.BookControler;

public class favorisAdapter extends RecyclerView.Adapter<favorisAdapter.MyViewHolderCat>  {
RecyclerView mRecyclerV;
        private Context context;
        // private Activity activity;
        //  private ArrayList titre, desc, ann, image;
        private ArrayList<Information> object;
    private ArrayList<Information> obj;

    //private MyViewHolder holder;
        //private int position;

        /* CustomAdapter(Activity activity, Context context, ArrayList titre, ArrayList desc,
                       ArrayList ann,ArrayList image){
             this.activity = activity;
             this.context = context;
             this.titre = titre;
             this.desc = desc;
             this.ann = ann;
             this.image = image;

         }*/
        public favorisAdapter(Context context, ArrayList<Information> object){
            this.context = context;
            this.object=object;

        }

        @NonNull
        @Override
        public MyViewHolderCat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.fav_book, parent, false);
            return new MyViewHolderCat(view);
        }




        public void onBindViewHolder(@NonNull final MyViewHolderCat holder, int position) {

            Information info= object.get(position);
            String tit=info.getTitre();
            String desc=info.getDescription();
            // String image=info.getImage();
            String ann=info.getYear();
            String id= info.getId();
            holder.book_ann_txt.setText(ann);
            holder.book_title_txt.setText(tit);
            String image= info.getImage();
            Picasso.get().load(image).into(holder.book_image);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, BookDetails.class);
                    intent.putExtra("BookId",id);
                    context.startActivity(intent);
                }
            });

          holder.star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                //    notifyItemRangeChanged(position, getItemCount(), null);

                    removedata(info,position)  ;












                }
            });



        }


        public void removedata(Information inf,int position){

            DatabaseReference ref= FirebaseDatabase.getInstance().getReference();

            String keyid = inf.getId();
            ref.child("favoris").child(keyid).removeValue();
            object.remove(position);
            notifyItemRemoved(position);

            BookControler bControler= BookControler.get_instance();
            bControler.updateData(keyid,0);
            Toast.makeText(context,"Removed from favorite", Toast.LENGTH_SHORT).show();

            //  notifyDataSetChanged();



        }



        @Override
        public int getItemCount() {
            return object.size();
        }

        class MyViewHolderCat extends RecyclerView.ViewHolder  {
            TextView book_title_txt, book_ann_txt ;
            ImageButton star,add;
            ImageView book_image;


            MyViewHolderCat(@NonNull View itemView) {
                super(itemView);

                book_image = itemView.findViewById(R.id.ICbookXML1);

                book_title_txt = itemView.findViewById(R.id.txtTitleXML1);
                star=itemView.findViewById(R.id.ICStarXML1);
                book_ann_txt = itemView.findViewById(R.id.txtYearXML1);




            }



        }

    }

