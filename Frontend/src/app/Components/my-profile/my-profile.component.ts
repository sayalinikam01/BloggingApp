import { Component} from '@angular/core';
import { PostService } from '../../services/post.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { CreateBlogComponent } from '../create-blog/create-blog.component';


@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent {

      blogPosts: any[] = [];
      constructor(private postService: PostService,private snackBar: MatSnackBar,public dialog: MatDialog){
      }
      ngOnInit(): void {
         this.postService.getPosts().subscribe(
         (posts:any[]) => {
              this.blogPosts=this.getPostsByUser(posts);
              if (this.blogPosts.length==0) this.NoPosts();
              //localStorage.setItem("totalPosts",this.blogPosts.length.toString());
         },
         (error)=>{
              console.log("error");
              this.NoPosts();
         });
      }


     getPostsByUser(posts:any[]) {
           return posts.filter(post => post.user.email === localStorage.getItem("user"));
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
        window.location.href="/myprofile";

      });

    }

}
