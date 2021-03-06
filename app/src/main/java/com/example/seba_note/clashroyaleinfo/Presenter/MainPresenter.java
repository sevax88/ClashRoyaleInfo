package com.example.seba_note.clashroyaleinfo.Presenter;

import android.util.Log;

import com.example.seba_note.clashroyaleinfo.App;
import com.example.seba_note.clashroyaleinfo.Model.Card;
import com.example.seba_note.clashroyaleinfo.View.MainActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter {

    private List<Card> mcards = new ArrayList<>();
    private Throwable error;
    private MainActivity view;

    public MainPresenter() {
        App.getServerAPI()
            .getCards("cards")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<Card>>() {
                @Override
                public void onCompleted() {
                    Log.e("","");
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("","");
                }

                @Override
                public void onNext(List<Card> cards) {
                    mcards.addAll(cards);
                    publish();
                }
            });
    }

    public void onTakeView(MainActivity view) {
        this.view = view;
        publish();
    }

    private void publish() {
        if (view != null) {
            if (mcards != null)
                view.onItemsNext(mcards);
            else if (error != null)
                view.onItemsError(error);
        }
    }
}
