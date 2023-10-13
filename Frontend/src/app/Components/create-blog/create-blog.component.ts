import { Component,EventEmitter, Output,Inject  } from '@angular/core';
import { MatDialogRef,MAT_DIALOG_DATA  } from '@angular/material/dialog';
import { PostService } from '../../services/post.service';
import { Router } from '@angular/router';

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
     @Inject(MAT_DIALOG_DATA) private data: any) { this.formData = { ...data };
        if(this.formData.content!=undefined)this.formData.content =  data.content.replace(/<br>/g, '\n');


          }

    onSubmit() {
      if(this.formData.title!=undefined && this.formData.content!=undefined ){
      console.log(this.formData);
      if(this.formData.postId===undefined){
        this.formData.content = this.formData.content.replace(/\n/g, '<br>');

        this.postService.createPost(this.formData);
         console.log("createblog")
         setTimeout(() => {
                       window.location.href = '/feed'; // Replace with the desired URL
                     }, 2000);

      }
      else {
        this.formData.content = this.formData.content.replace(/\n/g, '<br>');
        this.postService.updatePost(this.formData.postId,this.formData);


      }
      }
      else{
           this.snackBar.open('Enter some content to post', 'OK', {
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
