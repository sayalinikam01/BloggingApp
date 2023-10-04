import { Component,Input } from '@angular/core';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent {
      user=localStorage.getItem("user")
    //  totalPosts=localStorage.getItem("totalPosts")
   //  @Input() totalposts: number=0;

}
