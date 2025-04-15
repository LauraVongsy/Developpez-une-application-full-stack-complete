export interface Article {
    id: number;
    title: string;
    content: string;
    themeId: string;
   createdAt: Date;
   updatedAt: Date;
   authorName: string;
   themeName: string;
}