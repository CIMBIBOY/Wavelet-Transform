package wavelet;

public class WaveletTransform {

    

    static double[] daub = 
    {
        /* 1 */     0.707106781187,  0.707106781187,
    
        /* 2 */     0.482962913145,  0.836516303738,
                    0.224143868042, -0.129409522551,
    
        /* 3 */     0.332670552950,  0.806891509311,
                    0.459877502118, -0.135011020010,
                    -0.085441273882,  0.035226291882,
    
        /* 4 */     0.230377813309,  0.714846570553,
                    0.630880767930, -0.027983769417,
                    -0.187034811719,  0.030841381836,
                    0.032883011667, -0.010597401785,
    
        /* 5 */     0.160102397974,  0.603829269797,
                    0.724308528438,  0.138428145901,
                    -0.242294887066, -0.032244869585,
                    0.077571493840, -0.006241490213,
                    -0.012580751999,  0.003335725285,
    
        /* 6 */     0.111540743350,  0.494623890398,
                    0.751133908021,  0.315250351709,
                    -0.226264693965, -0.129766867567,
                    0.097501605587,  0.027522865530,
                    -0.031582039318,  0.000553842201,
                    0.004777257511, -0.001077301085,
    
        /* 7 */     0.077852054085,  0.396539319482,
                    0.729132090846,  0.469782287405,
                    -0.143906003929, -0.224036184994,
                    0.071309219267,  0.080612609151,
                    -0.038029936935, -0.016574541631,
                    0.012550998556,  0.000429577973,
                    -0.001801640704,  0.000353713800,
    
        /* 8 */     0.054415842243,  0.312871590914,
                    0.675630736297,  0.585354683654,
                    -0.015829105256, -0.284015542962,
                    0.000472484574,  0.128747426620,
                    -0.017369301002, -0.044088253931,
                    0.013981027917,  0.008746094047,
                    -0.004870352993, -0.000391740373,
                    0.000675449406, -0.000117476784,
    
        /* 9 */     0.038077947364,  0.243834674613,
                    0.604823123690,  0.657288078051,
                    0.133197385825, -0.293273783279,
                    -0.096840783223,  0.148540749338,
                    0.030725681479, -0.067632829061,
                    0.000250947115,  0.022361662124,
                    -0.004723204758, -0.004281503682,
                    0.001847646883,  0.000230385764,
                    -0.000251963189,  0.000039347320,
    
        /* 10 */    0.026670057901,  0.188176800078,
                    0.527201188932,  0.688459039454,
                    0.281172343661, -0.249846424327,
                    -0.195946274377,  0.127369340336,
                    0.093057364604, -0.071394147166,
                    -0.029457536822,  0.033212674059,
                    0.003606553567, -0.010733175483,
                    0.001395351747,  0.001992405295,
                    -0.000685856695, -0.000116466855,
                    0.000093588670, -0.000013264203
    };

    public static int Size;    

    private float[] calcbuf;

    public void waveletTransform(float[] v, int delta, int L, int M, int inc) 
    {
        if(calcbuf == null) calcbuf = new float[Size];
        int i, j, k, l;
        int a, b, c;
        int h, g, h0, g0;

        l = 1 << L;  h0 = M * (M - 1);  g0 = M * (M + 1) - 1;

        for (i = 0; i < L; i++) 
        {
            for(a=0, b=delta;  a<l;  a++, b+=inc) 
                calcbuf[a] = v[b]; // vektor segédbufferbe másolása
            
            l/=2; // a vektort kétfelé választjuk
            
            for(j=0, a=delta, b=delta+l*inc;  j<l;  j++, a+=inc, b+=inc) // vektor elemek
            {
                v[a]=v[b]=0;  c=2*j;  h=h0;  g=g0; // init
                
                for(k=0; k<M; k++) // momentum, két összegzés
                {
                    v[a] += daub[h] * calcbuf[c];  v[b] += daub[g] * calcbuf[c];  c++;  h++;  g--;
                    v[a] += daub[h] * calcbuf[c];  v[b] -= daub[g] * calcbuf[c];  c++;  h++;  g--;
                    if(c==2*l) c=0;
                }
            } // end for vektorelemek
        }
    }

    public void inverseWaveletTransform(float[] v, int delta, int L, int M, int inc) {
        int i, j, k, len;
        int a, b, c;
        int h, g, h0, g0;

        len = 1;
        h0 = M * (M - 1);
        g0 = M * (M + 1) - 1;

        for (i = 0; i < L; i++) 
        {
            for(a=0,b=delta;  a<2*len;  a++,b+=inc) 
                calcbuf[a] = v[b]; // segédbufferbe másolás
           
            for(j=0,a=delta,b=delta+inc;  j<len;  j++,a+=2*inc,b+=2*inc) // vektor elemek
            {
                v[a]=v[b]=0;  c=j;  h=h0;  g=g0; // init
                for(k=0; k<M; k++) // két összegzés
                {
                    v[a] += daub[h] * calcbuf[c] + daub[g] * calcbuf[c+len];  h++;  g--;
                    v[b] += daub[h] * calcbuf[c] - daub[g] * calcbuf[c+len];  h++;  g--;
                    if(c==0) c=len-1;  else c--;
                }
            } // end for vektorelemek
            len*=2; // a vektor kétszeresésre növekszik
        }
    }

    public void process(int mom, float[] matrix, int size, int level) 
    {
        int i;
        for(i=0; i<size; i++) waveletTransform(matrix, i*size, level, mom, 1); // sorok
	    for(i=0; i<size; i++) waveletTransform(matrix, i, level, mom, Size); // oszlopok
    }    

    public void reverse(int mom, float[] matrix, int size, int level)
    {
        int i;
        for(i=0; i<size; i++) inverseWaveletTransform(matrix, i*size, level, mom, 1); // sorok
	    for(i=0; i<size; i++) inverseWaveletTransform(matrix, i, level, mom, Size); // oszlopok
    }
}