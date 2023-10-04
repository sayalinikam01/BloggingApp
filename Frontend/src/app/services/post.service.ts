import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class PostService {
  url="http://localhost:8081"

  constructor(private http:HttpClient) { }

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
         return this.http.put(`${this.url}/posts/${PostId}`,formData).subscribe(
             (response) => {
                console.log('Response from the server:', response);
             },
             error => {
             console.error('Error updating post:', error);
             });
         }
}
