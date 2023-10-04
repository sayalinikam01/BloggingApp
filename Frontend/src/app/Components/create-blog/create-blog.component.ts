import { Component,EventEmitter, Output,Inject  } from '@angular/core';
import { MatDialogRef,MAT_DIALOG_DATA  } from '@angular/material/dialog';
import { PostService } from '../../services/post.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-create-blog',
  templateUrl: './create-blog.component.html',
  styleUrls: ['./create-blog.component.css']
})
export class CreateBlogComponent {
   formData: any = {
      title: '',
      content: '',
      image: null
    };

     constructor(private postService: PostService,private router: Router,
     public dialogRef: MatDialogRef<CreateBlogComponent>,
     @Inject(MAT_DIALOG_DATA) private data: any) { this.formData = { ...data }; }

    onSubmit() {
      console.log(this.formData);
      if(this.formData.postId===undefined){
        this.postService.createPost(this.formData);
      }
      else {
      this.postService.updatePost(this.formData.postId,this.formData);
      }
      this.dialogRef.close();
    }

//     onFileSelected(event: any) {
//       const file = event.target.files[0];
//       this.formData.image = file;
//     }
}
