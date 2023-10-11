import { Component} from '@angular/core';
import { PostService } from '../../services/post.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { CreateBlogComponent } from '../create-blog/create-blog.component';
import {Post} from '../../models/post.model';
import { Router } from '@angular/router';
@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent {

      blogPosts: Post[] = [];
      constructor(private postService: PostService,private snackBar: MatSnackBar,public dialog: MatDialog,private router:Router){
      }

      ngOnInit(): void {
        this.getPosts();
      }


    getPosts(){
        this.postService.getPosts().subscribe(
        (posts:any[]) => {
            this.blogPosts=posts.filter(post => post.user.email === localStorage.getItem("user"));
            this.blogPosts.forEach(post => {
                 this.postService.getTotReactions(post);
                 this.postService.getReactionsStatus(post.postId,post);
            });
            if (this.blogPosts.length==0) this.NoPosts();
        },
        (error)=>{
             console.log("error");
             this.NoPosts();
       });

    }

    getTotReactions(post){
         this.postService.getReactions(post.postId).subscribe(
         (response:any)=>{
             post.totLikes=response.likes;
             post.totHearts=response.hearts;
             post.totCelebration=response.celebration; },
             error=>{ console.log("error:",error)});
    }

     private NoPosts() {
         this.snackBar.open('No Posts Found', 'OK', {
         duration: 5000, });
     }

    showPopup = false;

    openPopupForm() {
      const dialogRef = this.dialog.open(CreateBlogComponent, {
        width: '800px',
        height: '600px',
    });


    dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
 });


    }


}
