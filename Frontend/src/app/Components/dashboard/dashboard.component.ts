import { Component } from '@angular/core';
import { PostService } from '../../services/post.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { CreateBlogComponent } from '../create-blog/create-blog.component';
import Swal from 'sweetalert2';
import {Post} from '../../models/post.model';
import { Router } from '@angular/router';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  blogPosts:  Post[] = [];

  constructor(private postService: PostService,private snackBar: MatSnackBar,public dialog: MatDialog,private router:Router) {}


  ngOnInit(): void {
      this.getPosts();
  }


  getPosts(){
     this.postService.getPosts().subscribe(
     (posts:any[]) => {
        this.blogPosts=posts;
        this.blogPosts.forEach(post => {
        this.postService.getTotReactions(post);
        this.postService.getReactionsStatus(post.postId,post);
        }
     );
     },
     (error)=>{
         console.log("error");
         this.NoPosts();
     });
  }

  private NoPosts() {
       this.snackBar.open('No Posts Found', 'OK', {
       duration: 5000, });
  }


  showPopup = false;
  openPopupForm() {
      const dialogRef = this.dialog.open(CreateBlogComponent, {
        width: '800px',
        height: '600px',});

      dialogRef.afterClosed().subscribe(result => {
      console.log("dashboard");
       this.postService.getPosts().subscribe(
                              (posts:any[]) => {

                                 this.blogPosts.forEach(post => {
                                 this.postService.getTotReactions(post);}
                              ); } );
              });
  }


}
