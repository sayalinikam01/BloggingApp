import { Component,Input ,ChangeDetectorRef ,OnInit,EventEmitter ,Output} from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute,Router } from '@angular/router';
import { PostService } from '../../services/post.service';
import { MatDialog } from '@angular/material/dialog';
import {MyProfileComponent } from '../my-profile/my-profile.component';
import { CreateBlogComponent} from '../create-blog/create-blog.component';
import { DatePipe } from '@angular/common';
import {Post} from '../../models/post.model';
import { BehaviorSubject, Observable } from 'rxjs';


@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})

export class PostComponent {

    @Input() blogPosts: Post[]=[];

    isMyProfilePage!: boolean;
    formattedDate: string="";
  private postsSubject = new BehaviorSubject<Post[]>(this.blogPosts);

    constructor(public dialog: MatDialog,private route: ActivatedRoute,private router: Router,private postService:PostService,private snackBar: MatSnackBar) {
      this.isMyProfilePage = this.route.snapshot.data['isMyProfilePage'];

    }

   ngOnInit(): void {


        }



       private NoPosts() {
           this.snackBar.open('No Posts Found', 'OK', {
           duration: 5000, });
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

   updatePost(postToUpdate,postId){
        console.log(postToUpdate);
        const dialogRef = this.dialog.open(CreateBlogComponent, {
               width: '800px',
                height: '600px',
                data:postToUpdate,
        });



     dialogRef.afterClosed().subscribe(result => {
//         setTimeout(() => {
//            window.location.href = '/myposts'; // Replace with the desired URL
//         }, 1000);

 } );

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
     this.postService.addReaction(postId,reactionId).subscribe(
           (response:any)=>{
              const post=this.blogPosts.find((post) => post.postId === postId);
              if(post) {
              post.totLikes=response.count;
              post.likedByUser=true;
             }
              else  console.log("count", response.count);

           },
           error=>{
                console.log("error:",error)
           });;
    }


  heart(postId,reactionId){
      this.postService.addReaction(postId,reactionId).subscribe(
      (response:any)=>{
          const post=this.blogPosts.find((post) => post.postId === postId);
          if(post) {
          post.totHearts=response.count;
          post.heartByUser=true;
          }
          else  console.log("count", response.count);
      },
      error=>{
           console.log("error:",error)
      });;
    }


    celebrate(postId,reactionId){
     this.postService.addReaction(postId,reactionId).subscribe(
           (response:any)=>{
                const post=this.blogPosts.find((post) => post.postId === postId);
                if(post) {
                post.totCelebration=response.count;
                post.celebrateByUser=true;
                }
                else  console.log("count", response.count);
           },
           error=>{
                console.log("error:",error)
           });;
    }
//



}
