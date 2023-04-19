package com.example.todolist;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddNoteViewModel extends AndroidViewModel {
    private NotesDAO notesDAO;
    private MutableLiveData<Boolean> shouldCloseScreen = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable=new CompositeDisposable();// disposable collection


    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        notesDAO = NoteDatabase.getInstance(application).notesDAO();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose(); //закрыли поток при уничножении активити
    }

    public void saveNote(Note note) {
        Disposable disposable = notesDAO.add(note) //закрывает потоки при уходе с экрана чтобы не было утечки памяти

                .subscribeOn(Schedulers.io()) //сам метод добавить выполнится в фоновом режиме
                .observeOn(AndroidSchedulers.mainThread())//все что ниже будет в главном потоке
                .subscribe(new Action() {
                    @Override

                    public void run() throws Throwable {
                        //метод выполнится после добавления заметки в базу
                        shouldCloseScreen.postValue(true);
                        //пост можно вызывать из любого потока а сэт только из главного

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d("AddNoteViewModel", "accept: error saveNote");
                    }
                }); //подписатлись на комплитабле
            compositeDisposable.add(disposable);


    }


    public LiveData<Boolean> getShouldCloseScreen() {
        return shouldCloseScreen;
    }
}
