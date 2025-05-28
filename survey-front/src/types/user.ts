export interface User {
  userId: number;
  userName: string;
  role: string;
}

export interface UserRequest {
  userId: number;
  userPwd: string;
}

export interface UserResponse {
  userId: number;
  userName: string;
  role: string;
}

export interface AdminLoginRequest {
  adminId: string;
  password: string;
}

export interface AdminLoginResponse {
  userName: string;
  role: string;
}

export interface AdminUsersResponse {
  userId: number;
  userName: string;
  loginDate: string;
  rgstDateTime: string;
} 