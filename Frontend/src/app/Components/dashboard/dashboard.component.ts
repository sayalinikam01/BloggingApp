import { Component } from '@angular/core';
import { PostService } from '../../services/post.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { CreateBlogComponent } from '../create-blog/create-blog.component';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  blogPosts: any[] = [];

  constructor(private postService: PostService,private snackBar: MatSnackBar,public dialog: MatDialog) {}


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

showPopup = false;
openPopupForm() {
      const dialogRef = this.dialog.open(CreateBlogComponent, {
        width: '800px',
        height: '600px',
    });


    dialogRef.afterClosed().subscribe(result => {
        console.log('The dialog was closed');
         setTimeout(() => {
                        window.location.href="/feed";
         }, 2000);



      });

    }

}
