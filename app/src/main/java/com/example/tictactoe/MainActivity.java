package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Boolean player1active;
    Button btnplay,btnreset;
    Button[] buttons = new Button[9];
    TextView txtsts,txtplayeronesc,txtplayertwosc;
    int plyaer1scn,player2scn;

    int[] gamestate={2,2,2,2,2,2,2,2,2,2};
    int[][] winpos={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    int rounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons[0]=(Button) findViewById(R.id.btn0);
        buttons[1]=(Button) findViewById(R.id.btn1);
        buttons[2]=(Button) findViewById(R.id.btn2);
        buttons[3]=(Button) findViewById(R.id.btn3);
        buttons[4]=(Button) findViewById(R.id.btn4);
        buttons[5]=(Button) findViewById(R.id.btn5);
        buttons[6]=(Button) findViewById(R.id.btn6);
        buttons[7]=(Button) findViewById(R.id.btn7);
        buttons[8]=(Button) findViewById(R.id.btn8);
        btnplay=(Button)findViewById(R.id.btnplayagain);
        btnreset=(Button)findViewById(R.id.btnreset);

        txtplayeronesc=(TextView)findViewById(R.id.txtp1);
        txtplayertwosc=(TextView)findViewById(R.id.txtp2);
        txtsts=(TextView) findViewById(R.id.txtstatus);
        for(int i=0;i<buttons.length;i++){
            buttons[i].setOnClickListener(this);
        }
        plyaer1scn=0;
        player2scn=0;
        player1active=true;
        rounds=0;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        if(!((Button)view).getText().toString().equals("")){
            return;
        } else if (checkWinner()) {
            return;
        }
        String btnid =view.getResources().getResourceEntryName(view.getId());
        int gamestatepointer = Integer.parseInt(btnid.substring(btnid.length()-1,btnid.length()));
        if(player1active){
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#ffc34a"));
            gamestate[gamestatepointer]=0;
        }else{
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#70fc3a"));
            gamestate[gamestatepointer]=1;
        }
        rounds++;
        if(checkWinner()){
            if(player1active){
                plyaer1scn++;
                UpdatePlayerScore();
                txtsts.setText("Player-1 is winner");
            }else{
                player2scn++;
                UpdatePlayerScore();
                txtsts.setText("Player-2 is winner");
            }
        } else if (rounds==9) {
            txtsts.setText("Oops nobody winn");
        }else {
            player1active=!player1active;
        }
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                plyaer1scn=0;
                UpdatePlayerScore();
            }
        });
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
            }
        });
    }

    private boolean checkWinner() {
        boolean winres = false;
        for(int [] winpos:winpos){
            if(gamestate[winpos[0]]==gamestate[winpos[1]]&&
                    gamestate[winpos[1]]==gamestate[winpos[2]]&&
                    gamestate[winpos[0]]!=2){
                winres=true;
            }
        }
        return  winres;
    }

    private void playAgain() {
        rounds=0;
        player1active=true;
        for(int i =0;i<buttons.length;i++){
            gamestate[i]=2;
            buttons[i].setText("");
        }
        txtsts.setText("Status");
    }

    private void UpdatePlayerScore() {
        txtplayeronesc.setText(Integer.toString(plyaer1scn));
        txtplayertwosc.setText(Integer.toString(player2scn));
    }
}