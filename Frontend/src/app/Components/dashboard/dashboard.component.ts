import { Component } from '@angular/core';
import { PostService } from '../../services/post.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  blogPosts: any[] = [];

  constructor(private postService: PostService,private snackBar: MatSnackBar) {}


  ngOnInit(): void {
      this.postService.getPosts().subscribe(
      (posts) => {
            this.blogPosts=posts;
            if (this.blogPosts.length==0) {this.NoPosts();}
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




}
