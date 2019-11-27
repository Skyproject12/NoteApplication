package com.example.noteapplication.ViewModel.ViewModelFactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.noteapplication.ViewModel.Home.HomeViewModel;
import com.example.noteapplication.ViewModel.NoteAddUpdate.NoteAddUpdateViewModel;

// this is for initial viewmodel

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;
    private Application application;

    // initial application for context viewmodel
    private ViewModelFactory(Application application){
        application= application;
    }

    public static ViewModelFactory getInstance(Application application){
        // initial instance in viewmodelfactory for coordinator rotation
        if(INSTANCE==null){
            synchronized (ViewModelFactory.class){
                if(INSTANCE==null){
                    INSTANCE= new ViewModelFactory(application);

                }
            }
        }
        return INSTANCE;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(HomeViewModel.class)){
            return (T) new HomeViewModel(application);

        }
        else if(modelClass.isAssignableFrom(NoteAddUpdateViewModel.class)){
            return (T) new NoteAddUpdateViewModel(application);

        }
        throw new IllegalArgumentException("Unknows ViewModel Class"+ modelClass.getName());

    }
}
