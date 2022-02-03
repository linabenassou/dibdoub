package dz.ubma.bookcollection_.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import dz.ubma.bookcollection_.BookDetails;
import dz.ubma.bookcollection_.Information;
import dz.ubma.bookcollection_.R;
import dz.ubma.bookcollection_.recycler.BookControler;

public class listAdapter extends RecyclerView.Adapter<listAdapter.MyViewHolderCategory> {

    private Context context;
   // private Activity activity;
  //  private ArrayList titre, desc, ann, image;
    private  ArrayList <Information> object;
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
    public listAdapter(Context context, ArrayList<Information> object){
        this.context = context;
        this.object=object;

    }
    @NonNull
    @Override
    public MyViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_book, parent, false);
        return new MyViewHolderCategory(view);
    }


    @SuppressLint("LongLogTag")
    @RequiresApi(api = Build.VERSION_CODES.M)

    public void onBindViewHolder(@NonNull final listAdapter.MyViewHolderCategory holder, int position) {

        Information info= object.get(position);
        String tit=info.getTitre();
        String desc=info.getDescription();
       // String image=info.getImage();
        String ann=info.getYear();
        String id= info.getId();
        String image= info.getImage();
        holder.book_ann_txt.setText(ann);
        holder.book_title_txt.setText(tit);
        Picasso.get().load(image).into(holder.book_image);
        //Log.d("lol",info.getImage());





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BookDetails.class);
                intent.putExtra("BookId",id);
                context.startActivity(intent);
            }
        });
        int iconStar = info.getIconStar()==1 ? R.drawable.ic_f_start: R.drawable.ic_star ;
        holder.star.setImageResource(iconStar);
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idBook= info.getId();
                info.setIconStar(info.getIconStar()==0? 1 : 0);
                int iconStar = info.getIconStar()==1 ? R.drawable.ic_f_start: R.drawable.ic_star ;
               if(info.getIconStar()==0){ removedata(info);

               }

                holder.star.setImageResource(iconStar);

                BookControler bControler= BookControler.get_instance();
                bControler.updateData(idBook,info.getIconStar());
                if(info.getIconStar()==1){
                addFavoris(info,holder,tit,ann,desc,info.getIconStar(),image);
                }

            }
        });


       /* Log.d("liliwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww", ann);
        holder.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


    }



    public void removedata(Information inf){

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference();

        String keyid = inf.getId();
        ref.child("favoris").child(keyid).removeValue();
        Toast.makeText(context,"Removed from favorite", Toast.LENGTH_SHORT).show();

    }

    private void addFavoris(Information inf,MyViewHolderCategory holder,String Titre,String Year,String Description,int icstar,String image) {
        String Id=inf.getId();
        Information info=new Information(Id,Titre,Description,Year,icstar,image);
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
        ref.child("favoris").child(Id).setValue(info);
        Toast.makeText(context,"Added to favorite", Toast.LENGTH_SHORT).show();


    }

    @Override
    public int getItemCount() {
        return object.size();
    }

    class MyViewHolderCategory extends RecyclerView.ViewHolder {
        private favorisAdapter myAdapter;

        TextView  book_title_txt, book_ann_txt ;
                ImageView book_image;
        ImageButton star,add;

        MyViewHolderCategory(@NonNull View itemView) {
            super(itemView);
            book_title_txt = itemView.findViewById(R.id.txtTitleXML);
            book_image = itemView.findViewById(R.id.ICbookXML);
            star=itemView.findViewById(R.id.ICStarXML);
            book_ann_txt = itemView.findViewById(R.id.txtYearXML);



        }


    }

}