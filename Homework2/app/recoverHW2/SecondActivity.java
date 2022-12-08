package com.example.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.homework2.databinding.ActivitySecondBinding;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   xi = 48,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\t"},
   d2 = {"Lcom/example/homework2/SecondActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/homework2/databinding/ActivitySecondBinding;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}
)
public final class SecondActivity extends AppCompatActivity {
   @Nullable
   private ActivitySecondBinding binding;

   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      ActivitySecondBinding var2 = ActivitySecondBinding.inflate(this.getLayoutInflater());
      int var4 = false;
      this.setContentView((View)var2.getRoot());
      this.binding = var2;
      ActivitySecondBinding var10000 = this.binding;
      if (var10000 != null) {
         ActivitySecondBinding $this$onCreate_u24lambda_u2d1 = var10000;
         int var5 = false;
         Intent intent = this.getIntent();
         $this$onCreate_u24lambda_u2d1.tv2.setText((CharSequence)intent.getStringExtra("extra_score"));
      }

   }
}
