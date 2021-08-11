package com.example.financeapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    // Used this for Java version:
    // https://medium.com/@zackcosborn/step-by-step-recyclerview-swipe-to-delete-and-undo-7bbae1fce27e
    // https://gist.github.com/keinix/b1aa2417dbea9311a1207eddf8b9d47b

    private TransactionAdapter mAdapter;

    private Drawable icon;
    private final ColorDrawable background;


    public SwipeToDeleteCallback(TransactionAdapter adapter) {
        //We want to be able to swipe right or left to delete it
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        //Set the delete icon for when the user swipes, getting the context from the adapter
        icon = ContextCompat.getDrawable(mAdapter.getContext(),
                R.drawable.ic_delete_white_24dp);
        //Set the background color to red
        background = new ColorDrawable(Color.RED);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // used for up and down movements
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mAdapter.deleteItem(position);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20; //so background is behind the rounded corners of itemView

        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
            //Changed from this:
            //int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            //int iconRight = itemView.getLeft() + iconMargin;
            // To this:
            // So we can still see the delete icon when we swipe right
            // It's in the GitHub comments: https://gist.github.com/keinix/b1aa2417dbea9311a1207eddf8b9d47b
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = iconLeft+ icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());

        } else if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        }
        else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);
    }

}


// My original code (doesn't work):


//    Context context;
//
//    // This line below causes an error:
//    Drawable deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_white_24dp);
//    int intrinsicWidth = deleteIcon.getIntrinsicWidth();
//    int intrinsicHeight = deleteIcon.getIntrinsicHeight();
//    ColorDrawable background = new ColorDrawable();
//    int backgroundColor = Color.parseColor("#f44336");
//
//    private Paint clearPaint = new Paint(){
//        Object xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR);
//    };
//    protected abstract Object PorterDuffXfermode(PorterDuff.Mode clear);
//
//    /**
//     * Creates a Callback for the given drag and swipe allowance. These values serve as
//     * defaults
//     * and if you want to customize behavior per ViewHolder, you can override
//     * {@link #getSwipeDirs(RecyclerView, ViewHolder)}
//     * and / or {@link #getDragDirs(RecyclerView, ViewHolder)}.
//     *
//     * @param dragDirs  Binary OR of direction flags in which the Views can be dragged. Must be
//     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
//     *                  #END},
//     *                  {@link #UP} and {@link #DOWN}.
//     * @param swipeDirs Binary OR of direction flags in which the Views can be swiped. Must be
//     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
//     *                  #END},
//     *                  {@link #UP} and {@link #DOWN}.
//     */
//    //This method is implemented because the class extends ItemTouchHelper.SimpleCallback
//    public SwipeToDeleteCallback (Context context) {
//        super(0, ItemTouchHelper.LEFT);
//        //Arguments changed to 0 and ItemTouchHelper.LEFT
//        this.context = context;
//    }
//
//
//
//    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
//        /**
//         * To disable "swipe" for specific item return 0 here.
//         * For example:
//         * if (viewHolder?.itemViewType == YourAdapter.SOME_TYPE) return 0
//         * if (viewHolder?.adapterPosition == 0) return 0
//         */
//        if(viewHolder.getAdapterPosition() == 10){
//            return 0;
//        }
//        return super.getMovementFlags(recyclerView, viewHolder);
//    }
//
//
//    public  boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target){
//        return false;
//    }
//
//    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, Float dx, Float dy, int actionState, Boolean isCurrentlyActive){
//        View itemView = viewHolder.itemView;
//        int itemHeight = itemView.getBottom() - itemView.getTop();
//        Boolean isCancelled = dx == 0f && !isCurrentlyActive;
//
//        if(isCancelled){
//            clearCanvas(c, itemView.getRight() + dx, ((float)itemView.getTop()), ((float)itemView.getRight()), ((float)itemView.getBottom()));
//            super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
//            return;
//        }
//
//        background.setColor(backgroundColor);
//        background.setBounds((int) ((int)itemView.getRight() + dx), itemView.getTop(), itemView.getRight(), itemView.getBottom());
//        background.draw(c);
//
//        int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
//        int deleteIconMargin = (itemHeight - intrinsicHeight) /2;
//        int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
//        int deleteIconRight = itemView.getRight() - deleteIconMargin;
//        int deleteIconBottom = deleteIconTop + intrinsicHeight;
//
//        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
//        deleteIcon.draw(c);
//
//        super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
//
//
//    }
//
//
//    private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom){
//        c.drawRect(left, top, right, bottom, clearPaint);
//    }
