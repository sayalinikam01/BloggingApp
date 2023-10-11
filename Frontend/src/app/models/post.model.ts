export interface User {
  userId: string;
  email: string;
  aboutMe: string;
  name: string;
  // Add other properties as needed
}

export interface Post {
  postId: string;
  user: User;
  title: string;
  content: string;
  image: string;
  createdDate: number;
  updatedDate: number;
  totLikes:number;
  totHearts:number;
  totCelebration:number;
  likedByUser: boolean;
  heartByUser:boolean;
  celebrateByUser:boolean;
  // Add other properties as needed
}
