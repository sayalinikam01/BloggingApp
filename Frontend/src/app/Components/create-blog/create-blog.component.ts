import { Component,EventEmitter, Output,Inject  } from '@angular/core';
import { MatDialogRef,MAT_DIALOG_DATA  } from '@angular/material/dialog';
import { PostService } from '../../services/post.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { MatSnackBar } from '@angular/material/snack-bar';

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

     constructor(private postService: PostService,private router: Router,private snackBar: MatSnackBar,
     public dialogRef: MatDialogRef<CreateBlogComponent>,
     @Inject(MAT_DIALOG_DATA) private data: any) { this.formData = { ...data }; }

    onSubmit() {
      console.log(this.formData);
      if(this.formData.postId===undefined){
        this.postService.createPost(this.formData);
        Swal.fire('Post Created!');
      }
      else {
      this.postService.updatePost(this.formData.postId,this.formData);
      this.snackBar.open('Post Updated', 'OK', {
            duration: 5000, });
      }
      this.dialogRef.close();
    }

    filename:String="" ;
    onFileSelected(event: any) {
      const file = event.target.files[0];
       if (file) {
            this.postService.uploadFile(file).subscribe(
              (response:any) => {
                  Swal.fire('Image uploaded!');
                  this.formData.image=response.url;
                  this.filename= file.name;
                  console.log(this.filename);
                console.log('Uploaded image URL:', response.url);
              },
              (error) => {
                console.error('Error uploading image:', error);
              }
            );
          }

      console.log( this.formData.image );
    }
}
