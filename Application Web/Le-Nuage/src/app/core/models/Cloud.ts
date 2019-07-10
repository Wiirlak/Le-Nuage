export class Cloud {

  constructor(
    public _id: string,
    public image: string = 'https://zupimages.net/up/19/26/afo4.png',
    public created: string = '1990-02-12',
    public name: string = '',
    public entities,
    public owner,
    public parentEntity: string
  ) {}
}
