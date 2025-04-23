export interface Comment{
    id?: number;
  content: string;
  authorName?: string;
  createdAt?: Date;
  updatedAt?: Date;
  authorId?: number;
  articleId: number;
}