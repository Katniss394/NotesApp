package com.judy.notesapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {

    Button add;
    ListView listView;
    EditText noteText;
    DatabaseHandler db;
    myAdapter adapter;
    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        add=(Button)getView().findViewById(R.id.addNote);
        listView=(ListView)getView().findViewById(R.id.list_view);
        noteText=(EditText)getView().findViewById(R.id.noteText);
        db=new DatabaseHandler(getActivity());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String note=noteText.getText().toString();
                db.addNote(new Note(note));
                noteText.setText(" ");
                List<Note> allNotes=db.getAll();
                adapter=new myAdapter(getActivity(),R.layout.row_item,allNotes);
                listView.setAdapter(adapter);

            }
        });
        List<Note> allNotes=db.getAll();
        adapter=new myAdapter(getActivity(),R.layout.row_item,allNotes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                final Note obj=(Note)listView.getAdapter().getItem(position);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView= inflater.inflate(R.layout.fragment_dialog, null);
                builder.setView(dialogView);
                final EditText mEditText =(EditText)dialogView.findViewById(R.id.updText);
                Button updateButton=(Button)dialogView.findViewById(R.id.updateBtn);
                Button deleteButton=(Button)dialogView.findViewById(R.id.deleteBtn);
                mEditText.setText(obj.getNote().trim());
                builder.setTitle("Delete or Edit");
                builder.setIcon(R.mipmap.edit);
                builder.create();
                builder.show();
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deletenote(obj);
                        String note=mEditText.getText().toString();
                        db.addNote(new Note(note));
                        List<Note> allNotes=db.getAll();
                        adapter=new myAdapter(getActivity(),R.layout.row_item,allNotes);
                        listView.setAdapter(adapter);
                        Toast.makeText(getActivity(),"Updated Successfully",Toast.LENGTH_SHORT).show();

                    }
                });
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        db.deletenote(obj);
                        Toast.makeText(getActivity(),"Deleted Successfully",Toast.LENGTH_SHORT).show();
                        List<Note> allNotes=db.getAll();
                        adapter=new myAdapter(getActivity(),R.layout.row_item,allNotes);
                        listView.setAdapter(adapter);

                    }
                });


            }
        });


    }
}
