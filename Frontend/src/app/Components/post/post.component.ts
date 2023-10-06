import { Component,Input ,OnInit} from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute,Router } from '@angular/router';
import { PostService } from '../../services/post.service';
import { MatDialog } from '@angular/material/dialog';
import { CreateBlogComponent} from '../create-blog/create-blog.component'
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})

export class PostComponent {

    @Input() blogPosts: any;

    isMyProfilePage!: boolean;
    formattedDate: string="";
    imgsrc:string="C://Users//sanika//OneDrive - SAS//Desktop//BloggingApp//BloggingApp//Backend//src//main//resources//static//Images//72e1a138-b9e8-44c2-a10e-befaa05c03a2-download.jpg";

    constructor(public dialog: MatDialog,private route: ActivatedRoute,private router: Router,private postService:PostService,private snackBar: MatSnackBar) {
      this.isMyProfilePage = this.route.snapshot.data['isMyProfilePage'];

    }

    ngOnInit():void{
        //this.formattedDate = this.formatDate(this.blogPosts.createdDate);
    }

  formatDate(date: any): string {
    const options: Intl.DateTimeFormatOptions = {
          year: 'numeric',
          month: 'long',
          day: 'numeric',
          hour: 'numeric',
          minute: 'numeric'
        };

        return new Intl.DateTimeFormat('en-US', options).format(date);
  }


    deletePostById(PostId: String)
    {
        this.postService.deletePost(PostId);
        this.deletePostFrontend(PostId);
        this.DeletedMsg();
    }

    updatePost(postToUpdate){
        console.log(postToUpdate);
        const dialogRef = this.dialog.open(CreateBlogComponent, {
               width: '800px',
                height: '600px',
                data:postToUpdate,
        });

        dialogRef.afterClosed().subscribe(result => {
                  console.log('The dialog was closed');
                   window.location.href="/myprofile";
                //  this.router.navigate(['/myprofile']);
        });
    }

     private deletePostFrontend(postId: String) {
           const index = this.blogPosts.findIndex(post => post.postId === postId);
           if (index !== -1) {
              this.blogPosts.splice(index, 1);
           }
              const cardToRemove = document.getElementById(`post-${postId}`);
           if (cardToRemove) {
               cardToRemove.remove();
           }
        }

     private DeletedMsg() {
         this.snackBar.open('Post Deleted', 'OK', {
         duration: 3000, });
     }

    like(postId,reactionId){
    console.log(postId,reactionId);
    }
    nolike=3;
    heart(postId,reactionId){
        console.log(postId,reactionId);
        }

    celebrate(postId,reactionId){
     console.log(postId,reactionId);
    }




}
