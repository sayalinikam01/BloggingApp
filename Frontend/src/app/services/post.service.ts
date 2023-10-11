import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ActivatedRoute,Router } from '@angular/router';
import {Post}  from '../models/post.model';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  url="http://localhost:8081"

  constructor(private http:HttpClient,private router: Router) { }

  getPosts():Observable<any[]>{
          return this.http.get<any[]>(`${this.url}/posts`)
    }


  createPost(formData: any) {
      console.log("service",formData)
      this.http.post(`${this.url}/posts`,formData).subscribe(
        (response) => {
          console.log('Response from the server:', response);
        },
        (error) => {
          console.error('Error:', error);
        }
      );
 }

   deletePost(PostId: String) {
     return this.http.delete(`${this.url}/posts/${PostId}`).subscribe(
         (response) => {
            console.log('Response from the server:', response);
         },
         error => {
         console.error('Error deleting post:', error);
         });
     }

   updatePost(PostId: String,formData: any) {
          this.http.put(`${this.url}/posts/${PostId}`,formData).subscribe(
             (response) => {
                console.log('Response from the server:', response);
             },
             error => {
             console.error('Error updating post:', error);
             });
         }

    uploadFile(file: File ){
        const formData: FormData = new FormData();
        formData.append('file', file,file.name);

         return this.http.post(`${this.url}/post/image/upload`, formData);
    }

    addReaction(postId,reactionId):Observable<any>{
        return this.http.post(`${this.url}/post/${postId}/reaction/${reactionId}`,{})

    }

    getReactions(postId){
        return this.http.get(`${this.url}/post/${postId}/totReaction`)

    }

   getTotReactions(post){
        this.getReactions(post.postId).subscribe(
        (response:any)=>{
                 post.totLikes=response.likes;
                 post.totHearts=response.hearts;
                 post.totCelebration=response.celebration; },
                 error=>{ console.log("error:",error)});
        }

   getReactionsStatus(postId,post){
     this.http.get(`${this.url}/post/${postId}/getReaction`).subscribe(
           (response:any)=>{
                post.likedByUser=response.like;
                post.heartByUser=response.hearts;
                post.celebrateByUser=response.celebration; },
                error=>{ console.log("error:",error)});
           }

}
